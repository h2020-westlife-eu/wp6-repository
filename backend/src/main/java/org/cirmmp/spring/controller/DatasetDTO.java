package org.cirmmp.spring.controller;

import java.util.Date;

public class DatasetDTO {

    public String name;
    public String info;
    public Date creation_date;
    public String summary;
    public String webdavurl;
    public Long projectId;
    public Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWebdavurl() {
        return webdavurl;
    }

    public void setWebdavurl(String webdavurl) {
        this.webdavurl = webdavurl;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
