package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.Project;
import org.cirmmp.spring.model.User;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("project")
@Transactional
public class ProjectDaoImpl extends AbstractDao<Integer, Project> implements ProjectDao{

    static final Logger logger = LoggerFactory.getLogger(ProjectDaoImpl.class);

    public Project findById(int id) {
        Project project = getByKey(id);

        return project;
    }

    public List<Project> findByUserId(int userId){
        logger.info("USER_ID : {}", userId);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("userId", userId));
        List<Project> project = (List<Project>)crit.list();
        return project;
    }

    public void save(Project project) {
        persist(project);
    }

    public void deleteById(String id){
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("Id", id));
         Project project = (Project)crit.uniqueResult();
        delete(project);
    }

    public List<Project> findAllProject(){
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("projectName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Project> project = (List<Project>) criteria.list();
        return project;
    }

}
