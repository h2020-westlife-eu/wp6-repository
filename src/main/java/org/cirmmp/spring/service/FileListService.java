package org.cirmmp.spring.service;

import org.cirmmp.spring.model.FileList;

import java.util.List;

public interface FileListService {

    FileList findById(int id);

   // List<FileList> findByProjectId(int project_id);

    void save(FileList fileList);

    void deleteById(int fillist_id);

    List<FileList> findAllFiles();
}
