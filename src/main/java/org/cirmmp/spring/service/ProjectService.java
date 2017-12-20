package org.cirmmp.spring.service;

import org.cirmmp.spring.model.Project;

import java.util.List;

public interface ProjectService {

    Project findById(Integer id);

    List<Project> findByUserId(int sso);

    void save(Project project);

    void deleteById(String id);

    List<Project> findAllProject();
    void updateProject(Project project);
}
