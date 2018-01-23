package org.cirmmp.spring.service;

import org.cirmmp.spring.dao.DataSetDao;
import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.model.rest.RestFileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("dataSetService")
public class DataSetServiceImpl implements DataSetService {

    @Autowired
    private DataSetDao dao;

    @Override
    public DataSet findById(Long id) {
        DataSet dataSet = dao.findById(id);
        return dataSet;
    }

    @Override
    public void save(DataSet dataSet) { dao.save(dataSet);

    }


    @Transactional
    @Override
    public List<FileList> FileFindById(Long id) {
        DataSet dataset = findById(id);
        List<FileList> filelist = dataset.getFileLists();
        return filelist;
    }

    @Transactional
    @Override
    public List<RestFileList> restFileFindById(Long id) {
        DataSet dataset = findById(id);
        List<FileList> filelist = dataset.getFileLists();
        List<RestFileList> outFile = new ArrayList<>();
        for(FileList file:filelist) {
            RestFileList restFileList = new RestFileList();
            restFileList.setFileName(file.getFileName());
            restFileList.setType(file.getType());
            restFileList.setCreation_date(file.getCreation_date());
            restFileList.setFileInfo(file.getFileInfo());
            restFileList.setCreation_date(file.getCreation_date());
            restFileList.setId(file.getId());
            outFile.add(restFileList);
        }
        return outFile;
    }

    @Transactional
    @Override
    public void deleteById(Long dataset_id) {
        dao.deleteById(dataset_id);

    }

    @Override
    public List<DataSet> findAllDataset() {
        return dao.findAllDataset();

    }
//    @Override
//    public void update(DataSet dataSet){
//        dao.findById(dataSet.getId());
//        dao.
//    }

}
