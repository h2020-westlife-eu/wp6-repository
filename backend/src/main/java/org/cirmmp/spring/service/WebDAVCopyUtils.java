package org.cirmmp.spring.service;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class WebDAVCopyUtils {


    @Synchronized
    public void copyFileToWebdav(String sourcepath, String targetwebdavdir) throws IOException{
        Sardine sardine = SardineFactory.begin();
        //InputStream fis = new FileInputStream(new File(sourcepath));
        //sardine.put(targetwebdavdir, fis);

        Path src = Paths.get(sourcepath);
        Path dest = Paths.get(targetwebdavdir);

        String webdavdirectory= "http://yourdavserver.com/adirectory/";

        try {
            sardine.createDirectory(webdavdirectory + targetwebdavdir);
            Files.walk(src)
                    .forEach(s ->
                    {
                        try {
                            Path d = dest.resolve(src.relativize(s));
                            if (Files.isDirectory(s)) {
                                if (!Files.exists(d))
                                    //Files.createDirectory( d );
                                    sardine.createDirectory(webdavdirectory + d.toString());
                                return;
                            }
                            //Files.copy( s, d );// use flag to override existing
                            InputStream fis = new FileInputStream(new File(s.toString()));
                            sardine.put(targetwebdavdir + s.toString(), fis);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void copyToWebdav(String sourcepath, String targetwebdavdir) throws IOException {
        //1.get recursively list of all files and subdirectories
        //2.create subdirectory as mkcol in target webdav
        //3.copy each file by copyFileToWebdav(file,targetwebdavdir+subdir)
    }


}
