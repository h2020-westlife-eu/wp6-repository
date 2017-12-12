package org.cirmmp.spring.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="PROJECT")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;


    @Column(name="USER_ID")
    private Integer userId;


    @Column(name="PROJECT_NAME")
    private String projectName;


    @Column(name="SUMMARY")
    private String summary;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;

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
        Id = id;
    }

    public Integer getId() {

        return Id;
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

