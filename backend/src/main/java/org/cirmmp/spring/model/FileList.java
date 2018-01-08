package org.cirmmp.spring.model;

import javax.persistence.*;

@Entity
@Table(name="FILELIST")
public class FileList {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="PROJECT_ID")
    private int projectId;

    @Column(name="FILE_NAME")
    private String fileName;

    @Column(name="FILE_INFO")
    private String fileInfo;

    @Column(name="type", length=100)
    private String type;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="content", nullable=true)
    private byte[] content;


    public String getType() {
        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }



    public byte[] getContent() {
        return content;
    }


    public Integer getId() {
        return id;
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


    public void setId(Integer id) {
        this.id = id;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }


}
