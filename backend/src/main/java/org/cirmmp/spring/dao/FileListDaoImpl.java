package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.service.TarService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("fileList")
@Transactional
public class FileListDaoImpl extends AbstractDao<Long, FileList> implements FileListDao {

    static final Logger logger = LoggerFactory.getLogger(FileListDaoImpl.class);




    @Override
    public FileList findById(Long id) {
        FileList filiList = getByKey(id);
        return filiList;
    }


//    @Override
//    public List<FileList> findByProjectId(int projectId) {
//        logger.info("PROJECT_ID : {}", projectId);
//        Criteria crit = createEntityCriteria();
//        crit.add(Restrictions.eq("projectId", projectId));
//        List<FileList> fileList = (List<FileList>)crit.list();
//        return fileList;
//    }

    @Override
    public void save(FileList fileList) {
        persist(fileList);
    }

    @Override
    public void deleteById(Long Id) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("id", Id));
        FileList fileList = (FileList) crit.uniqueResult();
        delete(fileList);

    }

    @Override
    public List<FileList> findAllFiles() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("fileName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<FileList> fileLists = (List<FileList>) criteria.list();
        return fileLists;
    }
}
