package org.cirmmp.spring.service;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import lombok.Synchronized;
import org.cirmmp.spring.controller.CopyTaskDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

//TODO is it needed to be component?
@Component
public class WebDAVCopyUtils {

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


}
