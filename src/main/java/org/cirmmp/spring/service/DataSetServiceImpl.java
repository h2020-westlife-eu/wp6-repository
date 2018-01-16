package org.cirmmp.spring.service;

import org.cirmmp.spring.dao.DataSetDao;
import org.cirmmp.spring.model.DataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dataSetService")
public class DataSetServiceImpl implements DataSetService{

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

    @Override
    public void deleteById(Long dataset_id) {
        dao.deleteById(dataset_id);

    }

    @Override
    public List<DataSet> findAllDataset() {
        return dao.findAllDataset();

    }
}
