package org.cirmmp.spring.service;

import org.cirmmp.spring.dao.DataSetDao;
import org.cirmmp.spring.dao.ProjectDao;
import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {


    private static final Logger logger = LoggerFactory
            .getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectDao dao;

    @Autowired
    private DataSetDao dataDao;

    //@Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public Project findById(Long id){
        return dao.findById(id);
    }

    //@Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Transactional
    @Override
    public List<DataSet> findDatasetByProjectId(Long id){
        Project entity = dao.findById(id);
        List<DataSet> empty = new ArrayList<>();
        if(entity.getDataset()!=null){
        List<DataSet> dataset = entity.getDataset();
            return dataset;
        } else {
            return empty;
        }
    }

    public List<Project> findByUserId(int sso){
        return dao.findByUserId(sso);
    }

    @Transactional
    public void save(Project project) {
        dao.save(project);
    }

    public void update(Project project) {
        dao.update(project);
    }

    @Transactional
    public void flushAndClear(){
        dao.flushAndClear();
    }

    public void deleteById(Long id){
        dao.deleteById(id);
    }

    public List<Project> findAllProject(){
        return dao.findAllProject();
    }

    public void updateProject(Project project) {
        Project entity = dao.findById(project.getId());
        if(entity!=null){
            entity.setProjectName(project.getProjectName());
            entity.setSummary(project.getSummary());
            entity.setUserId(project.getUserId());
        }
    }

    @Transactional
    public void deleteDataSet(Project project, DataSet dataset){
        Project entity = dao.findById(project.getId());
        List<DataSet> datasets = entity.getDataset();
        Long dataId = dataset.getId();
        entity.removeDataSet(dataset);
        dao.flushAndClear();
        dataDao.deleteById(dataId);
        //dao.save(entity);
    }


//    @Transactional
//    public void fileUpdateProject(Project project, FileList fileList){
//        Project entity = dao.findById(project.getId());
//        if(entity!=null){
//            if(project.getDataset()!=null){
//                entity.addDataSet(DataSet);
//            }
//        }
//        dao.save(entity);
//    }
}

