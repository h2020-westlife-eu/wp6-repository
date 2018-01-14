package org.cirmmp.spring.service;

import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.model.Project;

import java.util.List;

public interface ProjectService {

    Project findById(Long id);

    List<Project> findByUserId(int sso);

    void save(Project project);

    void deleteById(int id);

    List<Project> findAllProject();

    void updateProject(Project project);

    void fileUpdateProject(Project pro, FileList fileList);

}
