package org.cirmmp.spring.model.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class JFileList {

    private int projectId;

    private String fileName;

    private String fileInfo;

    private MultipartFile[] files;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date creation_date;

    private int dataSetId;

    private String summary;

    private String info;

    private String webdavurl;

    private int projectid;

    public MultipartFile[] getFiles() {

        return files;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getFiletName() {
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public int getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(int dataSetId) {
        this.dataSetId = dataSetId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWebdavurl() {
        return webdavurl;
    }

    public void setWebdavurl(String webdavurl) {
        this.webdavurl = webdavurl;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }
}
