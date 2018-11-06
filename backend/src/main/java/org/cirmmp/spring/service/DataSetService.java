package org.cirmmp.spring.service;

import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.model.rest.RestFileList;

import java.util.List;

public interface DataSetService {
    DataSet findById(Long id);

    //TODO DataSet findByProjectId(Long project_id);

    List<RestFileList> restFileFindById(Long id);

    List<FileList> FileFindById(Long id);

    void save(DataSet dataSet);
    void saveExisting(DataSet dataSet);

    void deleteById(Long dataset_id);

    List<DataSet> findAllDataset();

    List<DataSet> findAllDatasetByUser(Long userId);

    //void update(DataSet dataSet);
}
