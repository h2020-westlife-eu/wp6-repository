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

    private static final String davDirectory= "http://yourdavserver.com/adirectory/";
    public void copyFromWebDAV(String in, String out) throws IOException{

        Sardine sardine = SardineFactory.begin();
        InputStream fis = new FileInputStream(new File(in));
        sardine.put(davDirectory+out, fis);

    }
}
