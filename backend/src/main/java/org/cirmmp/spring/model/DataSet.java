package org.cirmmp.spring.model;

import org.cirmmp.spring.metadata.MetadataDB;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
/*
* TODO
* */

@Entity
@Table(name="DATASET")
public class DataSet {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME")
    private String dataName;

    @Column(name="INFO")
    private String dataInfo;

    @Column(name="TYPE", length=100)
    private String type;

    @Column(name="SUMMARY")
    private String summary;

    @Column(name="URI")
    private String uri;

    @Transient //ignored in DB - it will be in mongodb
    private String metadata; //unstructured metadata, variable types, but same for all files in dataset,
    //metadata per file in dataset metadata or in separate structure?
    //separate structure => more clear
    //in dataset metadata => it's not where it should be discoverable - preparation to export to metadata db - b2note,neo4j,redis,virtuoso
    //

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {       this.uri = uri;    }

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

    public String getMetadata(){ this.metadata= MetadataDB.getMetadata(this.id);return this.metadata;}

    public void setMetadata(String m) {this.metadata=m;MetadataDB.insertMetadata(this.id,this.metadata);}
}
