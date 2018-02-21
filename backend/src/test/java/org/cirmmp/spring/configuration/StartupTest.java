package org.cirmmp.spring.configuration;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.SpringServletContainerInitializer;

public class StartupTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Class.forName("org.springframework.web.SpringServletContainerInitializer" );
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
	public void test() throws Exception {
		start();
	}

	public static void start()  {
		SpringServletContainerInitializer init = new org.springframework.web.SpringServletContainerInitializer();
		MockServletContext context = new MockServletContext();
		Set<Class<?>> classes = Collections.singleton(AppInitializer.class);
		ServletContextEvent event = new MockServletContextEvent(context);
		try {
			init.onStartup(classes , context );
			context.contextInitialized(event );
		} catch (Exception e) {
			Throwable cause = e;
			while (null!=cause.getCause()) {cause = cause.getCause();}
			throw new RuntimeException(cause);
		}
	}

}
