package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("hibernateGeneral")
@Transactional
public class HibernateGeneralDaoImpl implements HibernateGeneralDao{
    @Autowired
    HibernateTemplate hibernateTemplate;

    static final Logger logger = LoggerFactory.getLogger(HibernateGeneralDaoImpl.class);

    @Override
    public List<DataSet> findAllDasetFronUserPrjects(int id) {

        Object[] queryParam = {id};
        String queryString = "select d from DataSet d where d.project.userId = ?";
        List<DataSet> dataSets = (List<DataSet>) hibernateTemplate.find(queryString,queryParam);
        return dataSets;

    }
}
