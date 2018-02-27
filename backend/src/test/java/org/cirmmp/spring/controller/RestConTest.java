package org.cirmmp.spring.controller;

import static org.junit.Assert.*;

import java.util.Collections;

import org.cirmmp.spring.configuration.AppConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author cm65
 * 
 * Test the intended use cases of the REST Controller
 * 
 * A separate set of security tests are also needed.
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes={ AppConfig.class }
)
public class RestConTest {
	

    private static final String EMAIL = "jd@nowhere.no";
	private static final String USERNAME = "username";
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
		this.restCon = this.applicationContext.getBean(
				RestCon.class
		);

		org.springframework.security.core.userdetails.User user = 
			new org.springframework.security.core.userdetails.User(USERNAME, "password", 
				true, true, true, true, 
				Collections.emptySet()
		);

        Authentication auth = new UsernamePasswordAuthenticationToken(user,null);

        SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@After
	public void tearDown() throws Exception {
	}

	/* 
	 * Not that this only tests the positive path, 
	 * so we can test the intended use cases of RestCon.
	 * This is not a security test 
	 * - it does not test the misuse cases. 
	 * */
	@Test
	public void testCheckAuthentication() {
		org.cirmmp.spring.model.User u = 
				this.restCon.checkAuthentication(USERNAME, "John Doe", EMAIL, "");
		assertEquals(EMAIL, u.getEmail());
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
	public void testListOrder() {
		fail("Not yet implemented");
	}
}
