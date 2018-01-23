package org.cirmmp.spring.model.json;

public class JProject {

    private String projectName;

    private String summary;


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getSummary() {
        return summary;
    }

}
