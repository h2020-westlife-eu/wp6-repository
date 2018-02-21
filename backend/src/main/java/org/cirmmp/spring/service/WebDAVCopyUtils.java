package org.cirmmp.spring.service;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class WebDAVCopyUtils {


    public void copyFileToWebdav(String sourcepath, String targetwebdavdir) throws IOException{
        Sardine sardine = SardineFactory.begin();
        InputStream fis = new FileInputStream(new File(sourcepath));
        sardine.put(targetwebdavdir, fis);
    }

    public void copyToWebdav(String sourcepath, String targetwebdavdir) throws IOException {
        //1.get recursively list of all files and subdirectories
        //2.create subdirectory as mkcol in target webdav
        //3.copy each file by copyFileToWebdav(file,targetwebdavdir+subdir)
    }


}
