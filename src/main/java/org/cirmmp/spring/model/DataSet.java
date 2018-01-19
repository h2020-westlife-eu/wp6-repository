package org.cirmmp.spring.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="DATASET")
public class DataSet {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @Column(name="DATASET_NAME")
    private String dataName;

    @Column(name="DATASET_INFO")
    private String dataInfo;

    @Column(name="TYPE", length=100)
    private String type;

    @Column(name="SUMMARY")
    private String summary;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATION_DATE")
    private Date creation_date;

    // This remove dataset and project
    //@ManyToOne(cascade = CascadeType.REMOVE)
    @ManyToOne
    // @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @OneToMany(mappedBy = "dataSet", cascade = CascadeType.ALL)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<FileList> fileLists ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

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

    public List<FileList> getFileLists() {
        return fileLists;
    }

    public void setFileLists(List<FileList> fileLists) {
        this.fileLists = fileLists;
    }
    public void addFileLists(FileList fileList){
        this.fileLists.add(fileList);
    }

    public void removeFileLists(FileList fileList){
        this.fileLists.remove(fileList);
    }
}
