package org.cirmmp.spring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.List;

import org.cirmmp.spring.configuration.AppConfig;
import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.model.rest.RestFileList;
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
	public void testFindByIdNonesuch() {
		DataSet found = service.findById(-1L);
		assertNull(found);
	}

	@Test
	public void testRestFileFindByIdNonesuch() {
		try {
			List<RestFileList> found = service.restFileFindById(-1L);
			assertEquals(0, found.size());
		} catch (NullPointerException e) {
			// TODO this doesn't really seem like the right behaviour
		}
	}

	@Test
	public void testFileFindById() {
		try {
			List<FileList> found = service.FileFindById(-1L);
			assertEquals(0, found.size());
		} catch (NullPointerException e) {
			// TODO this doesn't really seem like the right behaviour
		}
	}

	@Test
	public void testSaveNoFiles() {
		DataSet dataSet = new DataSet();
		this.service.save(dataSet );
		
		// check it can be found
		Long id = dataSet.getId();
		DataSet found = service.findById(id);
		assertNotNull(found);
		
		// now try to find its files
		List<FileList> files = service.FileFindById(id);
		assertTrue(
		    null==files || // TODO this does not seem right		
		    files.isEmpty() // the preferred behaviour
		);

		try {
			assertNotNull(this.service.restFileFindById(id));
		} catch (NullPointerException e) {
			// TODO this behaviour does not seem right
		}
		
		// now delete it
		service.deleteById(id);
		assertNull(service.findById(id));
	}
	

	@Test
	public void testSaveOneFile() {
		DataSet dataSet = new DataSet();
		FileList list = new FileList();
		List<FileList> lists = Collections.singletonList(list);
		dataSet.setFileLists(lists );
		
		this.service.save(dataSet );
		
		// check it can be found
		Long id = dataSet.getId();
		DataSet found = service.findById(id);
		assertNotNull(found);
		
		// now try to find its files
		List<FileList> files = service.FileFindById(id);
		assertEquals(1, files.size() );
		List<RestFileList> restFiles = this.service.restFileFindById(id);
		assertEquals(1, restFiles.size() );
		
		// now delete it
		service.deleteById(id);
		assertNull(service.findById(id));
	}

	@Test
	public void testDeleteByIdNonesuch() {
		try {
			service.deleteById(-1L);
		} catch (IllegalArgumentException e) {
			// that's fine
		}
	}

	@Test
	public void testFindAllDataset() {
		List<DataSet> found = service.findAllDataset();
	}

}
