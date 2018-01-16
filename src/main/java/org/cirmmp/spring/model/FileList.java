package org.cirmmp.spring.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="FILELIST")
public class FileList {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

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
   // @JoinColumn(name = "PROJECT_ID")
    private DataSet dataSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
}
