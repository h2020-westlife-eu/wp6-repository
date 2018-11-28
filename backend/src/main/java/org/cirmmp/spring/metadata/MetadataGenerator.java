package org.cirmmp.spring.metadata;

import org.cirmmp.spring.model.DataSet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/** this class exposes harvestMetadata method in order to generate metadata from dataset and files */
public class MetadataGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(MetadataGenerator.class);
    /** map between file extension and implementation of metadata extractor*/
    //lazy initialization, map between extension and class
    private static HashMap<String,Class> type2extractor;
    //map between extractor class name and instance
    private static HashMap<String,AMetadataExtractor> class2extractorobj;

    /**contains array of objects already initialized for extension type
     * add new extractor class implementation with filename extension here,
     * E.g. put("png",ExifMetadataExtractor.class)
     *
     * the class will be instantiated automatically when it is needed*/
    static {
        type2extractor = new HashMap<String,Class>();
        type2extractor.put("txt",TxtMetadataExtractor.class);
        type2extractor.put("",DefaultMetadataExtractor.class);
    }

    /** harvest metadata among all files presented in dataset directory,
     * returns string representation of JSON structure {'name':'datasetroot',items:['filename':'datasetfilename','key',value',...,]} */
    public static String harvestMetadata(DataSet dto, String srcpath) {
        return harvestJSONMetadata(dto,srcpath).toString();
    }
    /** harvest metadata among all files presented in dataset directory,
     * returns JSON structure {'name':'datasetroot',items:['filename':'datasetfilename','key',value',...,]} */
    public static JSONObject harvestJSONMetadata(DataSet dto, String srcpath) {
        class2extractorobj = new HashMap<String,AMetadataExtractor>();
        JSONArray items =  new JSONArray();
        JSONObject meta = new JSONObject();
        //traverse through srcpath and read headers of all files and put metadata structure to it
        Path src = Paths.get(srcpath);
        meta.put("name",dto.getDataName());
        meta.put("summary",dto.getSummary());
        meta.put("info",dto.getDataInfo());
        meta.put("url",dto.getUri());
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
                            } else { //it is file
                                //read header of the file
                                items.put(harvestFile(fileordir));
                                //System.err.println("current metadata:"+items.toString());
                            }

                            //TODO throw some exception to handle it by client code and to get feedback to UI!
                        } catch (IOException ie){
                            LOG.warn("ignoring error when processing file:" + fileordir.getFileName() + "\n");
                            ie.printStackTrace();
                        } catch (Exception e) {
                            //ct.setStatus(ct.getStatus()+"\nError    : "+e.getMessage());
                            LOG.error("non-ignoring error when processing file:" + fileordir.getFileName() + "\n");
                            e.printStackTrace();
                            throw e;
                        }
                    });
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            class2extractorobj.clear();
        }
        meta.put("items",items);
        LOG.debug("returning metadata");
        return meta;
    }

    /** lazy instatiation of object - creates single instance for class - cache instances in hashmap class2extractorobj */
    private static AMetadataExtractor getExtractor(Path fileordir) {
        String ext =getFileExtension(fileordir);
        //gets class implementing extension
        Class extclass = type2extractor.get(ext);
        //set default if extension is not recognized
        if (extclass == null) extclass = DefaultMetadataExtractor.class;
        //get existing instance if it was already instantiated
        AMetadataExtractor cobj = class2extractorobj.get(extclass.getName());

        if (cobj==null) {
            try { //try to create class using default constructor
                //Constructor ct = extclass.getConstructor(extclass);
                AMetadataExtractor obj2 = (AMetadataExtractor) extclass.newInstance();
                class2extractorobj.put(extclass.getName(),obj2);
                return obj2;
            } catch (Exception e){
                //create default extractor
                LOG.error("error creating class for extension "+ext);
                LOG.error(e.getMessage());
                e.printStackTrace();
                AMetadataExtractor obj2 = new DefaultMetadataExtractor();
                class2extractorobj.put(extclass.getName(),obj2);
                return obj2;
            }
        } else return cobj;
    }

    private static String getFileExtension(Path fileordir){
        String name = fileordir.getFileName().toString();
        int lastDot = name.lastIndexOf(".");
        if (lastDot ==-1) return "";
        return name.substring(lastDot+1);
    }

    public static JSONObject harvestFile(Path fileordir) throws IOException, FileNotFoundException {
     return getExtractor(fileordir).harvestFile(fileordir);
    }

}
