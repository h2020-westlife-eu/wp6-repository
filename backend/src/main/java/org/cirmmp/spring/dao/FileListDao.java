package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.model.Project;
import org.cirmmp.spring.model.User;

import java.util.List;

public interface FileListDao {

    FileList findById(int id);

    List<FileList> findByProjectId(String project_id);

    void save(FileList fileList);

    void deleteById(String fillist_id);

    List<FileList> findAllFiles();
}
