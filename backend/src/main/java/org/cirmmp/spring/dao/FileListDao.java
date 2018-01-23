package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.FileList;

import java.util.List;


public interface FileListDao {

    FileList findById(Long id);

  //  List<FileList> findByProjectId(int project_id);

    void save(FileList fileList);

    void deleteById(Long fillist_id);

    List<FileList> findAllFiles();
}
