package org.cirmmp.spring.metadata;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Extracts from mixed binary and text file - expects that text information is in the beginning and prefixed by #_$
 * maximum 16 normal lines are parsed or 4 binary files are tolerated then metadata extraction is stopped
 * detects loop of variables in form
 * loop_
 * _name1
 * _name2
 * _name3
 * value1 value2 value3
 * value1 value2 value3
 *
 * detects uncategorized values - e.g. one value in line -puts into "uncategorized" section
 *
 * detects values prefixed by $ - puts into "other" section
 *
 * detects metadata prefixed by # - tries to guess key and value
 *
 * detects key prefixed by ##$ and array of values on subsequent rows in JCAMPD format
 *
 */
public class DefaultMetadataExtractor implements AMetadataExtractor {

    public static final int MAX_BINLINES = 4;
    private  final Logger LOG = LoggerFactory.getLogger(DefaultMetadataExtractor.class);
    public  int MAX_NORMLINES = 16;
    protected int normlines=0;
    private  boolean expectvalues=false;
    private  String expectkey="";
    private  JSONArray loop;
    private  ArrayList<String> loopkeys;
    private  boolean inloop=false;
    private int loopindex=0;

    private  final String DATETIMEREGEX= "/\\d{4}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d(?:\\.\\d+)?Z?/";
    private  final String ASCIIPATTERN = "\\A\\p{ASCII}*\\z";

    /** returns metadata in JSONObject generated from content of the file */
    public JSONObject harvestFile(Path fileordir) throws IOException,FileNotFoundException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileordir.toFile()),"US-ASCII"));
        String st="",key="",value="";
        JSONObject meta =new JSONObject();
        JSONArray unc = new JSONArray();
        JSONArray dolar = new JSONArray();
        normlines=0; inloop=false;expectvalues=false;loopindex=0;
        //JSONObject loop =new JSONObject();
        metaput(meta,"filename",fileordir.getFileName().toString());
        try {
            metaput(meta, "modified", Files.getLastModifiedTime(fileordir).toString());
        } catch (Exception e) {//ignore
        }
        int binaryrows=0; //counts binary data in strings
        LOG.debug("processing file:"+fileordir.getFileName());
        try {
            while (((st = br.readLine()) != null) && (binaryrows < MAX_BINLINES) && (normlines < MAX_NORMLINES)) { //will end when 4 binary (nonascii) rows were read or MAX_NORMLINES normline (text)
                //parse line
                //JSCAMP starts with ## or $$
                if (!st.matches(ASCIIPATTERN)) {
                    binaryrows++;
                    LOG.debug("binary line detected:" + binaryrows);
                } else {
                    //detect $$ or ## - metadata
                    if (st.startsWith("#")) {
                        if (inloop) storeloop(meta);
                        parseHash(st, meta, unc);
                    } else if (st.startsWith("$")) parseDolar(st, meta, dolar);
                    else if (st.startsWith("loop_")) parseLoop(meta);
                    else if (st.startsWith("_")) {
                        if (inloop) parseLoopItem(st);
                        else parseNorm(st, meta, unc);
                    } else if (st.equals(";")) {
                        if (inloop) storeloop(meta);
                    } else parseNorm(st, meta, unc);
                }
                //count binary rows in data - after 4, it will finish
            }
        } catch (IOException e){
            LOG.error("Error when reading file"+fileordir.getFileName()+" error:"+e.getMessage());
        } finally {
            br.close();
        }
        //adds uncategorized (not distinguished key and value) items into the metadata
        if (unc.length()>0) meta.put("uncategorized",unc);
        if (dolar.length()>0) meta.put("other",dolar);
        if (inloop) storeloop(meta);
        LOG.debug("finished procesing file"+fileordir.getFileName());
        return meta;
    }

    //parses line with string without any prefix (#,$,_) normal string - not special character at the begining
    private  void parseNorm(String st, JSONObject meta,JSONArray unc) {
        //LOG.debug("parseNorm"+st+" inloop:"+inloop+" expectvalues:"+expectvalues);
        if (inloop){
            //put loop items as array ['axis':1,'id:'cbf',...]
            JSONObject loopitem = new JSONObject();
            Pattern p= Pattern.compile("[A-Za-z0-9\\.,_-]+|\"[^\"]+\"|\'[^\']+\'"); //should split by space, keeps quoted items together
            Matcher m = p.matcher(st);
            for (int i = 0; i < loopkeys.size(); i++) {
                if (m.find()) {
                    loopitem.put(loopkeys.get(i).replace('.', ':'), m.group(0));
                }
            }
            LOG.debug("added loop items:"+loopitem.toString());
            loop.put(loopitem);
            return;
        }
        if (expectvalues) {
            //put array of values in this row as expectkey
            JSONArray values = new JSONArray(st.trim().split(" "));
            LOG.debug("adding expected values - array:"+values.toString());
            metaput(meta,expectkey,values);
            return;
        }
        if (normlines++> MAX_NORMLINES) return; //parse only first MAX_NORMLINES normlines
        String[] splitted = st.trim().split(":", 2);
        if (splitted.length > 1)
            metaput(meta,splitted[0], splitted[1].trim()); //trim e.g. spaces
        else {
            splitted = st.split(" ", 2);
            if (splitted.length > 1)
                metaput(meta,splitted[0], splitted[1].trim());
            else
                //puts whole string into uncategorized array of items
            {String uncvalue=st.trim(); if (uncvalue.length()>0) unc.put(uncvalue);}
            //do nothing else
            //metaput(meta,"", st); //empty key
        }
    }

    //puts metadata key and value - keys should not contain some special characters e.g.(.)
    private  void metaput(JSONObject meta, String key, Object value){
        //keys in mongodb is not recommended to contain dot
        
        meta.put(key.replace('.',':'),value);
    }

    private  void parseDolar(String st, JSONObject meta,JSONArray unc) {
        //removes $$ and puts content into an uncategorized array as an item
        LOG.debug("parsing Dolar:"+st);
        unc.put(st.substring(st.lastIndexOf('$')).trim());
    }


    private  void storeloop(JSONObject meta){
        LOG.debug("storing loop:",loop.toString());
        meta.put("loop_"+(++loopindex),loop);
        inloop=false;
    }

    private  void parseLoop(JSONObject meta) {
        if (inloop){ storeloop(meta);        }
        //stores previous loop
        //if (!loop.isEmpty()) {meta.put("loop",loop)};
        //loop.
        LOG.debug("start parsing Loop");
        //unc.put(st.substring(st.lastIndexOf('$')).trim());
        loop=new JSONArray();
        loopkeys=new ArrayList<>();
        inloop=true;
    }
    private  void parseLoopItem(String item){
        LOG.debug("loop item:"+item);
        loopkeys.add(item.substring(1)); //remove leading _
    }


    protected void initNormlines() {normlines=0;}

    //parses hash string - very probably meatadata
    private  void parseHash(String st2, JSONObject meta,JSONArray unc) {
        if (st2.startsWith("##JCAMPDX")) metaput(meta,"file-format","JCAMPDX");
        if (st2.startsWith("####CBF")) metaput(meta,"file-format","CIF BINARY FORMAT");
        initNormlines();
        expectvalues=false;
        //LOG.debug("parse Hash:"+st2);
        //remove hashes
        String st = st2.substring(st2.lastIndexOf('#')+1).trim();
        //remove dolars - can't store dolar keys
        st= st.substring(st.lastIndexOf('$')+1);
        String[] splitted = st.split("=", 2);
        if (splitted.length > 1) {
            String value=splitted[1];
            if(value.endsWith(")")) {expectvalues=true;expectkey=splitted[0];LOG.debug("expected key:"+expectkey);}
            metaput(meta,splitted[0], splitted[1].trim()); //trim e.g. spaces
        }
        else {
            if (st.trim().matches(DATETIMEREGEX)) {
                metaput(meta,"datetime",st.trim());
            } else {
                //split first by comma
                splitted = st.split(":",2);
                if (splitted.length==1) splitted = st.split("[\\(\\)]",2);
                //then by colon
                if (splitted.length==1) splitted = st.split(",",2);
                //then by space
                if (splitted.length==1) splitted = st.split(" ",2);
                if (splitted.length > 1)
                    metaput(meta,splitted[0].trim(), splitted[1].trim());
                else
                    unc.put(st.trim()); //empty key uncategorized values
            }
        }
    }

}
