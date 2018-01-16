package org.cirmmp.spring.service;

import org.cirmmp.spring.dao.ProjectDao;
import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao dao;

    //@Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public Project findById(Long id){
        return dao.findById(id);
    }

    //@Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Transactional
    @Override
    public  List<DataSet> findDatasetByProjectId(Long id){
        Project entity = dao.findById(id);
        List<DataSet> dataset = entity.getDataset();
        return dataset;
    }

    public List<Project> findByUserId(int sso){
        return dao.findByUserId(sso);
    }

    public void save(Project project) {
        dao.save(project);
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

