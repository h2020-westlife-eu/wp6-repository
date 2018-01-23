package org.cirmmp.spring.model;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="PROJECT")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DataSet> dataset ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public List<DataSet> getDataset() {
        return dataset;
    }

    public void setDataset(List<DataSet> dataset) {
        this.dataset = dataset;
    }

    public void addDataSet(DataSet dataset){
        this.dataset.add(dataset);
    }

    public void removeDataSet(DataSet dataSet){
        this.dataset.remove(dataSet);
    }
}

