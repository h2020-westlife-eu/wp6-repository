package org.cirmmp.spring.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="FILELIST")
public class FileList {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

//    @Column(name="PROJECT_ID")
//    private int projectId;

    @Column(name="FILE_NAME")
    private String fileName;

    @Column(name="FILE_INFO")
    private String fileInfo;

    @Column(name="TYPE", length=100)
    private String type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATION_DATE")
    private Date creation_date;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="CONTENT", nullable=true)
    private byte[] content;

    @ManyToOne
    private Project project;


    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

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


    public String getFileName() {
        return fileName;
    }

    public String getFileInfo() {
        return fileInfo;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }


}
