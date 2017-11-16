package org.cirmmp.spring.service;

import org.cirmmp.spring.dao.ProjectDao;
import org.cirmmp.spring.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao dao;

    public Project findById(Integer id){
        return dao.findById(id);
    }

    public List<Project> findByUserId(int sso){
        return dao.findByUserId(sso);
    }

    public void save(Project project) {
        dao.save(project);
    }

    public void deleteById(String id){
        dao.deleteById(id);
    }

    public List<Project> findAllProject(){
        return dao.findAllProject();
    }
}

