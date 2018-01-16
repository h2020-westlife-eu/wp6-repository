package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.DataSet;


import java.util.List;

public interface DataSetDao {

    DataSet findById(Long id);

    //  List<FileList> findByProjectId(int project_id);

    void save(DataSet dataSet);

    void deleteById(Long dataset_id);

    List<DataSet> findAllDataset();
}
