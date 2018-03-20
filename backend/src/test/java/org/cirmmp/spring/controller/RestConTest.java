package org.cirmmp.spring.controller;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cirmmp.spring.configuration.AppConfig;
import org.cirmmp.spring.model.json.JProject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private ProjectCon projectCon;
	private DatasetServiceCon datasetCon;
	private org.springframework.security.core.userdetails.User  user;

	@Before
	public void setUp() throws Exception {
		this.restCon = this.applicationContext.getBean(
				RestCon.class
		);
		this.projectCon = this.applicationContext.getBean(
				ProjectCon.class
		);
		this.datasetCon = this.applicationContext.getBean(
				DatasetServiceCon.class
		);


		this.user = 
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
		ResponseEntity<?> response = this.restCon.listUsers();
		assertEquals(HttpStatus.OK, response.getStatusCode() );
	}
	
	// example "{\"id\":629,\"userId\":59,\"creation_date\":\"Feb 27, 2018 5:23:37 PM\"}	";
	static final Pattern ID  = Pattern.compile( "\"id\"\\:(\\d+)," );
	
	@Test
	public void rt() {
		assertTrue( Pattern.compile("a").matcher("aa").find() );
	}
	
	@Test
	public void testCreateProject() {
		
		// create a project
		JProject jProject = new JProject();
		ResponseEntity<?> response = this.projectCon.createProject(
				jProject ,
				this.user.getUsername(), "", "", ""
		);
		assertEquals(HttpStatus.OK, response.getStatusCode() );
		
		Matcher m = ID.matcher((String)response.getBody());
		assertTrue((String)response.getBody(), m.find());
		String id = m.group(1);
		
		// check it is in the list of projects
		response = this.projectCon.listProject(
				this.user.getUsername(), "", "", ""
		);
		assertEquals(HttpStatus.OK, response.getStatusCode() );
		// check new project is in list		
		String json = (String) response.getBody();
		assertTrue( ((String)response.getBody()).contains(id) );
		
		// list its files
		response = this.datasetCon.listDataset(Optional.of(Long.parseLong(id)));//(Optional.of( Long.parseLong(id) ));
		List datasets = (List) response.getBody();
		assertEquals(0, datasets.size());
		
		//TODO delete the project again
	}


	@Test
	public void testListFiles() {
		ResponseEntity<?> response = this.projectCon.listFiles();
		assertEquals(HttpStatus.OK, response.getStatusCode() );
		// TODO check response
	}

	@Test
	public void testListDataset() {
		
		// all projects
		Optional<Long> nonesuch = Optional.empty();
		ResponseEntity<?> response = this.datasetCon.listDataset(nonesuch );
		assertEquals(HttpStatus.OK, response.getStatusCode() );
		List datasets = (List) response.getBody();
		// TODO check response
		
		// non-existent project 
		response = this.datasetCon.listDataset(Optional.of(-1L));
		// TODO shouldn't that be 404?
		datasets = (List) response.getBody();
		assertEquals(0, datasets.size());
	}


	@Test
	public void testListOrder() {
		ResponseEntity<?> response = this.restCon.listOrder(
				this.user.getUsername(), "", "", ""
		);
		assertEquals(HttpStatus.OK, response.getStatusCode() );
		// TODO check response
	}
}
