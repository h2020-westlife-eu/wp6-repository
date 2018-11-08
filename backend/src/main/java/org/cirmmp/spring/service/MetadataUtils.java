package org.cirmmp.spring.service;

import com.mongodb.*;
import org.cirmmp.spring.controller.RestCon;
import org.cirmmp.spring.model.DataSet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MetadataUtils {
    private static final Logger LOG = LoggerFactory.getLogger(MetadataUtils.class);

    /** harvest metadata among all files presented in dataset directory, returns structure {'name':'datasetroot',items:['filename':'datasetfilename','key',value',...,]} */
    public static String harvestMetadata(DataSet dto, String srcpath){
        //String meta="{}";
        JSONArray items =  new JSONArray();//.put("")
        JSONObject meta = new JSONObject();
        //traverse through srcpath and read headers of all files and put metadata structure to it
        Path src = Paths.get(srcpath);
        metaput(meta,"name",dto.getDataName());
        metaput(meta,"summary",dto.getSummary());
        metaput(meta,"info",dto.getDataInfo());
        metaput(meta,"url",dto.getUri());

        //now construct items - list of metadata of files in dataset
        try {
            Files.walk(src)
                    .forEach(fileordir ->
                    {
                        try {

                            //relative path is needed only in directory branch of condition?
                            Path relativedir = src.relativize(fileordir);//Path d = dest.resolve(src.relativize(fileordir));
                            //source is dir - create dir in target
                            if (Files.isDirectory(fileordir)) {
                                //DO NOTHING ??
                                //ct.setStatus(ct.getStatus()+"\nDirectory: "+fileordir);
                                //Path relativedir = src.relativize(fileordir);
                                //cannot check directory existence in WEBDAV this way
                                //if (!Files.exists(d))
                                //sardine.createDirectory(targetwebdavdir +"/"+ relativedir.toString());
                                //do not return, we need to continue to traverse the path
                                //return;
                            } else { //it is file
                                //read header of the file
                                items.put(harvestFile(fileordir));
                                //System.err.println("current metadata:"+items.toString());
                            }
                            //parse header - get to json
                            //ct.setStatus(ct.getStatus()+"\nFile     : "+fileordir);
                            // Files.copy( s, d );// use flag to override existing
                            //InputStream fis = new FileInputStream(new File(fileordir.toString()));
                            //sardine.put(targetwebdavdir +"/"+ relativedir.toString(), fis);

                            //TODO throw some exception to handle it by client code and to get feedback to UI!
                        } catch (Exception e) {
                            //ct.setStatus(ct.getStatus()+"\nError    : "+e.getMessage());
                            System.err.print("error when processing file:"+fileordir.getFileName()+"\n");
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            //System.err.print("error when processing file:"+fileordir.getFileName()+"\n");
            //ct.setStatus(ct.getStatus()+"\nError    : "+e.getMessage());
            e.printStackTrace();
        }
        meta.put("items",items);
        //System.err.println("returning metadata:"+meta.toString());
        return meta.toString();
    }

    private static final String DATETIMEREGEX= "/\\d{4}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d(?:\\.\\d+)?Z?/";
    private static final String ASCIIPATTERN = "\\A\\p{ASCII}*\\z";

    private static JSONObject harvestFile(Path fileordir) throws IOException, FileNotFoundException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileordir.toFile()),"US-ASCII"));
        String st,key,value;
        JSONObject meta =new JSONObject();
        JSONArray unc = new JSONArray();
        JSONArray dolar = new JSONArray();
        //JSONObject loop =new JSONObject();
        metaput(meta,"filename",fileordir.getFileName().toString());
        metaput(meta,"modified",Files.getLastModifiedTime(fileordir).toString());
        int binaryrows=0; //counts binary data in strings
        LOG.error("processing file:"+fileordir.getFileName());
        while (((st = br.readLine()) != null) && (binaryrows<4)) { //will end when 4 binary (nonascii) rows were read
            //parse line
            //JSCAMP starts with ## or $$
            if (! st.matches(ASCIIPATTERN)) {
                binaryrows++;
                LOG.error("binary line detected:"+binaryrows);
            } else {
                //detect $$ or ## - metadata
                if (st.startsWith("#")) parseHash(st, meta,unc);
                else if (st.startsWith("$")) parseDolar(st,meta,dolar);
                else if (st.startsWith("loop_")) parseLoop(meta);
                else if (st.startsWith("_")) parseLoopItem(st);
                else if (st.equals(";")) storeloop(meta);
                else parseNorm(st, meta,unc);
            }
            //count binary rows in data - after 4, it will finish
        }
        //adds uncategorized (not distinguished key and value) items into the metadata
        if (unc.length()>0) meta.put("uncategorized",unc);
        if (dolar.length()>0) meta.put("other",dolar);
        if (inloop) storeloop(meta);
        br.close();
        return meta;
    }

    //parses normal string - not special character at the begining
    private static void parseNorm(String st, JSONObject meta,JSONArray unc) {
        if (inloop){
            //put loop items as array ['axis':1,'id:'cbf',...]
            JSONObject loopitem = new JSONObject();
            String[] values=st.split("\\w+|\"[^\"]+\""); //should split by space, keeps quoted items together
            if (values.length==loopkeys.size()) {
                for (int i = 0; i < loopkeys.size(); i++) {
                    loopitem.put(loopkeys.get(i), values[i]);
                }
                loop.put(loopitem);
                return;
            } else {
                LOG.error("not loop row, different size, parsing as normal:"+st);
            }
        }
        if (expectvalues) {
            //put array of values in this row as expectkey
            metaput(meta,expectkey,st.trim().split(" "));
            return;
        }
        String[] splitted = st.trim().split(":", 2);
        if (splitted.length > 1)
            metaput(meta,splitted[0], splitted[1].trim()); //trim e.g. spaces
        else {
            splitted = st.split(" ", 2);
            if (splitted.length > 1)
                metaput(meta,splitted[0], splitted[1].trim());
            else
                //puts whole string into uncategorized array of items
                unc.put(st.trim());
            //do nothing else
            //metaput(meta,"", st); //empty key
        }
    }
    
    private static void metaput(JSONObject meta, String key, String value){
        //keys in mongodb is not recommended to contain dot
        if (key.contains(".")) key.replace('.',':');
        meta.put(key,value);
    }
    private static void metaput(JSONObject meta, String key, String[] value){
        //keys in mongodb is not recommended to contain dot
        if (key.contains(".")) key.replace('.',':');
        meta.put(key,value);
    }

    private static void parseDolar(String st, JSONObject meta,JSONArray unc) {
        //removes $$ and puts content into an uncategorized array as an item
        LOG.error("parsing Dolar:"+st);
        unc.put(st.substring(st.lastIndexOf('$')).trim());
    }

    private static JSONArray loop;
    private static ArrayList<String> loopkeys;
    private static boolean inloop=false;

    private static void storeloop(JSONObject meta){
        meta.put("loop",loop);
        inloop=false;
    }

    private static void parseLoop(JSONObject meta) {
        if (inloop){ storeloop(meta);        }
        //stores previous loop
        //if (!loop.isEmpty()) {meta.put("loop",loop)};
        //loop.
        //LOG.error("parsing Loop:"+st);
        //unc.put(st.substring(st.lastIndexOf('$')).trim());
        loop=new JSONArray();
        loopkeys=new ArrayList<>();
        inloop=true;
    }
    private static void parseLoopItem(String item){
        loopkeys.add(item.substring(1)); //remove leading _

    }

    private static boolean expectvalues=false;
    private static String expectkey="";

    //parses hash string - very probably meatadata
    private static void parseHash(String st2, JSONObject meta,JSONArray unc) {
        expectvalues=false;
        LOG.error("parse Hash:"+st2);
        String st = st2.substring(st2.lastIndexOf('#')).trim();
        String[] splitted = st.split("=", 2);
        if (splitted.length > 1) {
            String value=splitted[1];
            if(value.endsWith(")")) {expectvalues=true;expectkey=splitted[0];}
            metaput(meta,splitted[0], splitted[1].trim()); //trim e.g. spaces
        }
        else {
            if (st.trim().matches(DATETIMEREGEX)) {
                metaput(meta,"datetime",st.trim());
            } else {
                splitted = st.split(" ", 2);
                if (splitted.length > 1)
                    metaput(meta,splitted[0].trim(), splitted[1].trim());
                else
                    unc.put(st.trim()); //empty key
            }
        }
    }

    private static DBCollection coll;
    //initialize mongodb connection
    static {
        try {
            MongoClient mongoClient = new MongoClient();
            DB db = mongoClient.getDB("repositorydb");
            coll = db.getCollection("metadata");
            LOG.error("mongodb connection initialized");
        } catch (Exception e)
        {
            coll=null;
            System.err.println("Error during initialization connection to MONGODB");
            e.printStackTrace();
        }
    }

    public static String insertMetadata(Long id,String md){
        if (coll!=null){
            LOG.error("inserting for id:"+id+" metadata:"+md);
            BasicDBObject obj = (BasicDBObject) com.mongodb.util.JSON.parse(md);
            obj.put("id",id);
            coll.insert(obj);
            LOG.error("inserted for id:"+id);
            return obj.toString();
        } else{
            LOG.error("error, not inserted for id:"+id);
            return ""; //some error?
        }
    }

    //get metadata from mongodb - object with "id" == id
    public static String getMetadata(Long id) {
        LOG.error("get metadata for id:"+id);
        if (coll!=null){
            //DBObject obj = (DBObject) com.mongodb.util.JSON.parse(md);
            DBObject obj = null;
            BasicDBObject query = new BasicDBObject("id", id);
            DBCursor cursor = coll.find(query);

            try {
                if(cursor.hasNext()) {
                    obj =cursor.next();
                }
            } finally {
                cursor.close();
            }
            LOG.error("returning metadata:");


            if (obj!=null) {
                LOG.error("m:"+obj.toString());
                return obj.toString();
            }
            else return "";
        } else{
            return ""; //some error?
        }
    }

}
