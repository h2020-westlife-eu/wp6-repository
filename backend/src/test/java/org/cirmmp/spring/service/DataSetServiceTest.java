package org.cirmmp.spring.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes={ AppConfig.class }
)
@Transactional()
public class DataSetServiceTest {

    @Autowired
    private ApplicationContext applicationContext;
	private DataSetService service;
	
	@BeforeClass
	public static void setUpBeforeClass()  {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.service = (DataSetService) this.applicationContext.getBean(
				"dataSetService"
				);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	

	public DataSetServiceTest() {
		super();
	}

	@Test
	public void testFindById() {
		DataSet found = service.findById(1L);
		assertNull(found);
	}

	@Test
	public void testRestFileFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFileFindById() {
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
	public void testFindAllDataset() {
		fail("Not yet implemented");
	}

}
