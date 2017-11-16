package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.Project;


import java.util.List;

public interface ProjectDao {

    Project findById(int id);

    List<Project> findByUserId(int sso);

    void save(Project project);

    void deleteById(String id);

    List<Project> findAllProject();
}
