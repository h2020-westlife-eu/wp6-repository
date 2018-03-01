package org.cirmmp.spring.dao;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.cirmmp.spring.configuration.AppConfig;
import org.cirmmp.spring.model.DataSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes={ AppConfig.class }
		//,loader = AnnotationConfigWebContextLoader.class
)
@Transactional(
		//transactionManager = "txMgr"
)
// do not @Commit
public class DataSetDaoTest {
	

    @Autowired
    private ApplicationContext applicationContext;

	private DataSetDao dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// none needed - spring context is cached
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// none needed
	}
	

    @BeforeTransaction
    public void verifyInitialDatabaseState() {
        // could have logic to verify the initial state before a transaction is started
    }



    @AfterTransaction
    public void verifyFinalDatabaseState() {
        // coudl have logic to verify the final state after transaction has rolled back
    }

    
	@Before
	public void setUp() throws Exception {
		this.dao = (DataSetDao) this.applicationContext.getBean(
				"dataSet"  
		);
	}

	@After
	public void tearDown() throws Exception {
		// none needed
	}

	@Test
	public void testFindByIdNonesuch() {
		assertNull( this.dao.findById(-1L) );
	}

	@Test
	public void testSave() {
		try {
			this.dao.save(null);
		} catch (IllegalArgumentException e) {
			// that's fine
		}
		
		DataSet ds = new DataSet();
		this.dao.save(ds );
		assertNotNull( this.dao.findById( ds.getId() ) );
	}

	@Test
	public void testDeleteById() {
		try {
			this.dao.deleteById(-1L);
		} catch (IllegalArgumentException e) {
			// that's fine
		}
		
		DataSet ds = new DataSet();
		this.dao.save(ds );
		this.dao.deleteById( ds.getId());
		assertNull( this.dao.findById( ds.getId() ) );
	}

	@Test
	public void testFindAll() {
		DataSet ds = new DataSet();
		this.dao.save(ds );
		List<DataSet> dss = this.dao.findAllDataset();
		for (Iterator iterator = dss.iterator(); iterator.hasNext();) {
			DataSet dataSet = (DataSet) iterator.next();
				if (ds.getId()==dataSet.getId()) {return;}
		}
		fail("New dataset not found");
	}


}
