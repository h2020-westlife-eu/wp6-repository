package org.cirmmp.spring.service;

import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.Project;

import java.util.List;

public interface ProjectService {

    Project findById(Long id);

    List<Project> findByUserId(int sso);

    void save(Project project);

    void update(Project project);

    void deleteById(Long id);

    void flushAndClear();

    List<Project> findAllProject();

    List<DataSet> findDatasetByProjectId(Long id);

    void updateProject(Project project);

    void deleteDataSet(Project project, DataSet dataset);
   // void fileUpdateProject(Project pro, FileList fileList);

}
