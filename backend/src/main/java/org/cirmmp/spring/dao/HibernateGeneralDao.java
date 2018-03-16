package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.DataSet;

import java.util.List;

public interface HibernateGeneralDao {

    List<DataSet> findAllDasetFronUserPrjects(int id);
}
