package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.DataSet;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("dataSet")
@Transactional
public class DataSetDaoImpl extends AbstractDao<Long, DataSet> implements DataSetDao {

    static final Logger logger = LoggerFactory.getLogger(DataSetDaoImpl.class);

    @Override
    public DataSet findById(Long id) {
        DataSet dataSet = getByKey(id);
        return dataSet;
    }

    @Override
    public void save(DataSet dataSet) {
        persist(dataSet);

    }

    @Override
    public void deleteById(Long dataset_id) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("id", dataset_id));
        DataSet dataSet = (DataSet) crit.uniqueResult();
        delete(dataSet);

    }

    @Override
    public List<DataSet> findAllDataset() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("dataName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<DataSet> dataSets = (List<DataSet>) criteria.list();
        return dataSets;
    }
}
