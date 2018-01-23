package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.Project;

import java.util.List;

public interface ProjectDao {

    Project findById(Long id);

    List<Project> findByUserId(int sso);

    void save(Project project);

    void deleteById(Long id);

    List<Project> findAllProject();

    void flushAndClear();

    void  update(Project project);
}
