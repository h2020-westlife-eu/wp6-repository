package org.cirmmp.spring.configuration;

import static org.junit.Assert.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppInitializerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Class.forName("org.cirmmp.spring.configuration.AppInitializer");
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
	public void testStartUp() throws ServletException {
		ServletContext context = new MockServletContext();
		new AppInitializer().onStartup(context);
	}
	
	@Test
	public void testGetRootConfigClasses() {
		new AppInitializer().getRootConfigClasses();
	}

	@Test
	public void testGetServletConfigClasses() {
		new AppInitializer().getServletConfigClasses();
	}

	@Test
	public void testGetServletMappings() {
		new AppInitializer().getServletMappings();
	}

	// TODO @Test
	public void testCustomizeRegistrationDynamic() {
		fail("Not yet implemented"); // TODO  		new AppInitializer().customizeRegistration(registration);
	}

}
