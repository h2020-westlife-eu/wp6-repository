package org.cirmmp.spring.service;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.cirmmp.spring.model.FileList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@Service("TarService")
public class TarServiceImpl implements TarService {


    private static final Logger logger = LoggerFactory
            .getLogger(TarServiceImpl.class);

    @Autowired
    DataSetService dataSetService;

    @Transactional
    public File createTarFile(String root, Long datasetId) {

        //create a random string
        String generatedString = RandomStringUtils.randomAlphanumeric(10);

        logger.info("genereted String-> "+generatedString);
        //check and create a directori
        File rootDir = new File(root);
        File directory = new File(rootDir,generatedString);

        //File directory = new File(SRC_FOLDER);

        //make sure directory exists
        if(!directory.exists()){
            System.out.println("Directory does not exist.");
            directory.mkdirs();

        }else{
            try{
                delete(directory);
                directory.mkdirs();
            }catch(IOException e){
                e.printStackTrace();
                System.exit(0);
            }
        }

        List<FileList> fileLists = dataSetService.FileFindById(datasetId);
        for (FileList file : fileLists) {

            byte[] fileBytes = file.getContent();
            String filename = file.getFileName();
            String dirFilename = new File(directory, filename).toString();

            try {
                FileOutputStream outputStream = new FileOutputStream(dirFilename);
                outputStream.write(fileBytes);
                outputStream.close();
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        }

        File source = new File(directory, "Dataset.tar.tgz");
        TarArchiveOutputStream tarOs = null;
        try {
            // Using input name to create output name
            FileOutputStream fos = new FileOutputStream(source.getPath());
            GZIPOutputStream gos = new GZIPOutputStream(new BufferedOutputStream(fos));
            tarOs = new TarArchiveOutputStream(gos);
            File folder = new File(directory.toString());
            File[] fileNames = folder.listFiles();
            for (File file : fileNames) {
                System.out.println("PATH " + file.getAbsolutePath());
                System.out.println("File name " + file.getName());
                addFilesToTarGZ(file.getAbsolutePath(), file, tarOs);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                tarOs.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return source;
    }

    /**
     * @param source
     * @param file
     * @param tos
     * @throws IOException
     */
    public void addFilesToTarGZ(String source, File file, TarArchiveOutputStream tos)
            throws IOException {
        // New TarArchiveEntry
        tos.putArchiveEntry(new TarArchiveEntry(file, source));
        if (file.isFile()) {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            // Write content of the file
            IOUtils.copy(bis, tos);
            tos.closeArchiveEntry();
            fis.close();
        } else if (file.isDirectory()) {
            // no need to copy any content since it is
            // a directory, just close the outputstream
            tos.closeArchiveEntry();
            for (File cFile : file.listFiles()) {
                // recursively call the method for all the subfolders
                addFilesToTarGZ(cFile.getAbsolutePath(), cFile, tos);

            }
        }

    }


    public static void delete(File file)
            throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }

        } else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }
}
