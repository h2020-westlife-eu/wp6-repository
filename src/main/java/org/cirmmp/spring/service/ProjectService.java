package org.cirmmp.spring.service;

import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.Project;

import java.util.List;

public interface ProjectService {

    Project findById(Long id);

    List<Project> findByUserId(int sso);

    void save(Project project);

    void deleteById(Long id);

    List<Project> findAllProject();

    List<DataSet> findDatasetByProjectId(Long id);

    void updateProject(Project project);

   // void fileUpdateProject(Project pro, FileList fileList);

}
