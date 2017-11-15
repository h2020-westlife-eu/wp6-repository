package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.model.Project;
import org.cirmmp.spring.model.User;

import java.util.List;

public interface FileListDao {

    Project findById(int id);

    Project findByProjectId(String project);

    void save(FileList fileList);

    void deleteById(String fillist_id);

    List<FileList> findAllFiles();
}
