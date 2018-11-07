package org.cirmmp.spring.service;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import lombok.Synchronized;
import org.cirmmp.spring.controller.CopyTaskDTO;
import org.cirmmp.spring.controller.DatasetDTO;
import org.cirmmp.spring.model.DataSet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

//TODO is it needed to be component?
@Component
public class WebDAVCopyUtils {

    private static final String ASCIIPATTERN = "\\A\\p{ASCII}*\\z";

    //TODO is synchronized needed? copyFileToWEBDAV can take long and there is probability
    //TODO that multiple requests will come, there is no access to shared resources
    //@Synchronized
    //TODO currently nowhere is thrown IOException, consider to throw something
    public static void copyFileToWebdav(String sourcepath, String targetwebdavdir,CopyTaskDTO ct) {
        Sardine sardine = SardineFactory.begin();
        //InputStream fis = new FileInputStream(new File(sourcepath));
        //sardine.put(targetwebdavdir, fis);

        Path src = Paths.get(sourcepath);
        //Path dest = Paths.get(targetwebdavdir);

        //WEBDAV dir is in targetwebdavdir - not hardcoded!
        //String webdavdirectory= "http://yourdavserver.com/adirectory/";

        try {
            ct.setStatus("");
            ct.setDone(false);
            //the targetwebdavdir exists already
            //sardine.createDirectory(targetwebdavdir);
            Files.walk(src)
                    .forEach(fileordir ->
                    {
                        try {

                            //relative path is needed only in directory branch of condition?
                            Path relativedir = src.relativize(fileordir);//Path d = dest.resolve(src.relativize(fileordir));
                            //source is dir - create dir in target
                            if (Files.isDirectory(fileordir)) {
                                ct.setStatus(ct.getStatus()+"\nDirectory: "+fileordir);
                                //Path relativedir = src.relativize(fileordir);
                                //cannot check directory existence in WEBDAV this way
                                //if (!Files.exists(d))
                                sardine.createDirectory(targetwebdavdir +"/"+ relativedir.toString());
                                //do not return, we need to continue to traverse the path
                                //return;
                            } else { //it is file
                                ct.setStatus(ct.getStatus()+"\nFile     : "+fileordir);
                                // Files.copy( s, d );// use flag to override existing
                                InputStream fis = new FileInputStream(new File(fileordir.toString()));
                                sardine.put(targetwebdavdir +"/"+ relativedir.toString(), fis);
                            }
                            //TODO throw some exception to handle it by client code and to get feedback to UI!
                        } catch (Exception e) {
                            ct.setStatus(ct.getStatus()+"\nError    : "+e.getMessage());
                            //e.printStackTrace();
                        }
                    });
            //TODO throw exception so it can be handled by client code
        } catch (Exception ex) {
            ct.setStatus(ct.getStatus()+"\nMain Error: "+ex.getMessage());
            //ex.printStackTrace();
        }

    }

    @Async
    public void asyncCopyTask(ArrayList<CopyTaskDTO> copytasks, int id, String src, String dest) {
        WebDAVCopyUtils.copyFileToWebdav(src,dest,copytasks.get(id));
        copytasks.get(id).setDone(true);

    }

    /** harvest metadata among all files presented in dataset directory, returns structure {'name':'datasetroot',items:['filename':'datasetfilename','key',value',...,]} */
    public static String harvestMetadata(DataSet dto, String srcpath){
        //String meta="{}";
        JSONArray items =  new JSONArray();//.put("")
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
        //ct.setStatus(ct.getStatus()+"\nError    : "+e.getMessage());
        e.printStackTrace();
        }
        meta.put("items",items);
        return meta.toString();
    }


    private static JSONObject harvestFile(Path fileordir) throws IOException,FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(fileordir.toString()));
        String st,key,value;
        JSONObject meta =new JSONObject();
        meta.put("filename",fileordir.getFileName());
        meta.put("modified",Files.getLastModifiedTime(fileordir));
        int binaryrows=0; //counts binary data in strings
        while (((st = br.readLine()) != null) || (binaryrows<4)) { //will end when 4 binary (nonascii) rows were read
            //parse line
            //JSCAMP starts with ## or $$
            if (st.startsWith("##")) {
                String [] splitted = st.split("=",2);
                if (splitted.length>1)
                    meta.put(splitted[0],splitted[1].trim()); //trim e.g. spaces
                else{
                    splitted = st.split(" ",2);
                    if (splitted.length>1)
                        meta.put(splitted[0],splitted[1]);
                    else
                        meta.put("",st); //empty key
                }
            }
            if (st.startsWith("$$")) {
                meta.put(st,"");
            }
            //count binary rows in data - after 4, it will finish
            if (! st.matches(ASCIIPATTERN)) binaryrows++;
        }
        br.close();
        return meta;
    }

}
