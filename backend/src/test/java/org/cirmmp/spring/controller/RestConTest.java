package org.cirmmp.spring.controller;

import static org.junit.Assert.*;

import org.cirmmp.spring.configuration.AppConfig;
import org.cirmmp.spring.service.DataSetService;
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

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)

// Or the @TestPropertySource annotation can be declared on a test class to declare resource locations for test properties files or inlined properties.
//  Alternatively, you can implement and configure your own custom SmartContextLoader for advanced use cases.
@ContextConfiguration(
		classes={ AppConfig.class }
		//,loader = AnnotationConfigWebContextLoader.class
)
public class RestConTest {
	

    @Autowired
    private ApplicationContext applicationContext;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private RestCon restCon;

	@Before
	public void setUp() throws Exception {
		this.restCon = (RestCon) this.applicationContext.getBean(
				RestCon.class
		);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testListUsers() {
		this.restCon.listUsers();
	}

	@Test
	public void testCreateProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testListProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testListFiles() {
		fail("Not yet implemented");
	}

	@Test
	public void testListDataset() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckAuthentication() {
		fail("Not yet implemented");
	}

}
