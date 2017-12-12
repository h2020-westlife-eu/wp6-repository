package org.cirmmp.spring.service;

import org.cirmmp.spring.dao.FileListDao;
import org.cirmmp.spring.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fileListService")
public class FileListServiceImpl implements FileListService {

    @Autowired
    private FileListDao dao;

    @Override
    public FileList findById(int id) {
        return dao.findById(id);
    }

    @Override
    public List<FileList> findByProjectId(String project_id) {
        return dao.findByProjectId(project_id);
    }

    @Override
    public void save(FileList fileList) {
        dao.save(fileList);

    }

    @Override
    public void deleteById(String fillist_id) {
        dao.deleteById(fillist_id);

    }

    @Override
    public List<FileList> findAllFiles() {
        return dao.findAllFiles();
    }
}
