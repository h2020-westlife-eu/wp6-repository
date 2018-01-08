package org.cirmmp.spring.model.json;

import org.springframework.web.multipart.MultipartFile;

public class JFileList {

    private int projectId;

    private String fileName;

    private String fileInfo;

    private MultipartFile[] files;


    public MultipartFile[] getFiles() {

        return files;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setFiletName(String filetName) {
        this.fileName = filetName;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }
}
