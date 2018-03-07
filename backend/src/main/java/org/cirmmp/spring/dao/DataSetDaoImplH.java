package org.cirmmp.spring.dao;


import org.cirmmp.spring.model.DataSet;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("dataSetH")
@Transactional
public class DataSetDaoImplH {

    @Autowired
    HibernateTemplate hibernateTemplate;

    static final Logger logger = LoggerFactory.getLogger(DataSetDaoImpl.class);


    public DataSet findById(Long id) {
        DataSet dataSet = hibernateTemplate.get(DataSet.class, id);
        //DataSet dataSet = getByKey(id);
        return dataSet;
    }


    public void save(DataSet dataSet) {
        hibernateTemplate.save(dataSet);
    }


    public void deleteById(Long dataset_id) {
        DetachedCriteria crit =  DetachedCriteria.forClass(DataSet.class);
        //Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("id", dataset_id));
        List<DataSet> dataSet = (List<DataSet>) hibernateTemplate.findByCriteria(crit);
        //DataSet dataSet = (DataSet) crit.uniqueResult();
        //delete(dataSet);
        hibernateTemplate.delete(dataSet);

    }


    public List<DataSet> findAllDataset() {
//        DetachedCriteria crit =  DetachedCriteria.forClass(DataSet.class);
//
//        Criteria criteria = createEntityCriteria().addOrder(Order.asc("dataName"));
//        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
//        List<DataSet> dataSets = (List<DataSet>) criteria.list();
//        return dataSets;
        List<DataSet> dataSet = hibernateTemplate.loadAll(DataSet.class);
        return dataSet;
    }

}
