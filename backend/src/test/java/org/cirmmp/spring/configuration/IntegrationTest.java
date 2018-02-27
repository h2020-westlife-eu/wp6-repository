package org.cirmmp.spring.configuration;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)

// Or the @TestPropertySource annotation can be declared on a test class to declare resource locations for test properties files or inlined properties.
//  Alternatively, you can implement and configure your own custom SmartContextLoader for advanced use cases.
@ContextConfiguration(
		classes={ AppConfig.class }
		//,loader = AnnotationConfigWebContextLoader.class
)
public class IntegrationTest  {	
	
	@Autowired
    private WebApplicationContext wac;


    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private MockServletContext servletContext; // cached
    

    @Autowired
    MockHttpServletRequest request;

    @Autowired
    MockHttpServletResponse response;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWiring() {
		assertNotNull(this.applicationContext);
		assertNotNull(this.wac);
		assertNotNull(this.servletContext);
		assertNotNull(this.request);
		assertNotNull(this.response);
	}
	
	@Test
	public void testSecurity() {
		assertNull( SecurityContextHolder.getContext().getAuthentication() );
	}


}
