package org.cirmmp.spring.model;

import javax.persistence.*;
import java.util.Date;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;

    //@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    //private Set<FileList> fileDocuments = new HashSet<FileList>();



    public void setCreation_date(Date creation_date) {

        this.creation_date = creation_date;
    }
    public Date getCreation_date() {

        return creation_date;
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

