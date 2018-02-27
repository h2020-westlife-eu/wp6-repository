package org.cirmmp.spring.dao;

import static org.junit.Assert.*;

import org.cirmmp.spring.configuration.AppConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Commit;
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
@Commit
public class ProjectDaoTest {
	

    @Autowired
    private ApplicationContext applicationContext;

	private ProjectDao dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	

    @BeforeTransaction
    public void verifyInitialDatabaseState() {
        // logic to verify the initial state before a transaction is started
    }



    @AfterTransaction
    public void verifyFinalDatabaseState() {
        // logic to verify the final state after transaction has rolled back
    }

    
	@Before
	public void setUp() throws Exception {
		// this.dao = new ProjectDaoImpl(); // TODO from context
		this.dao = (ProjectDao) this.applicationContext.getBean(
				"project" //ProjectDaoImpl.class
				);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindById() {
		assertNull( this.dao.findById(-1L) );
	}

	@Test
	public void testFindByUserId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testFlushAndClear() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

}
