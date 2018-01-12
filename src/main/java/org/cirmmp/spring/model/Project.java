package org.cirmmp.spring.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="PROJECT")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="USER_ID")
    private Integer userId;

    @Column(name="PROJECT_NAME")
    private String projectName;

    @Column(name="SUMMARY")
    private String summary;

    @Column(name="SHERABLE")
    private String shearable;

    @Column(name="HASH_ID")
    private String hashId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATION_DATE")
    private Date creation_date;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<FileList> fileLists ;


    public List<FileList> getFileLists() {
        return fileLists;
    }

    public void setFileLists(List<FileList> fileLists) {
        this.fileLists = fileLists;
    }

    public void setCreation_date(Date creation_date) {

        this.creation_date = creation_date;
    }
    public Date getCreation_date() {

        return creation_date;
    }


    public String getShearable() {
        return shearable;
    }

    public void setShearable(String shearable) {
        this.shearable = shearable;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public void setId(Integer id) {
        id = id;
    }

    public Integer getId() {

        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getSummary() {
        return summary;
    }




}

