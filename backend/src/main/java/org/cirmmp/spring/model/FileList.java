package org.cirmmp.spring.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

public class FileList {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer fileList_id;

    @Column(name="PROJECT_ID")
    private String projectId;

    @Column(name="FILE_NAME")
    private String filetName;

    @Column(name="FILE_INFO")
    private String fileInfo;



    public Integer getFileList_id() {
        return fileList_id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getFiletName() {
        return filetName;
    }

    public String getFileInfo() {
        return fileInfo;
    }



    public void setFileList_id(Integer fileList_id) {
        this.fileList_id = fileList_id;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setFiletName(String filetName) {
        this.filetName = filetName;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }


}
