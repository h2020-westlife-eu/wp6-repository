package org.cirmmp.spring.controller;

import static org.junit.Assert.*;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cirmmp.spring.configuration.AppConfig;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ModelMap;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes={ AppConfig.class }
)
public class AppControllerTest {
	

	private static final String USERNAME = "AppControllerTest"+System.currentTimeMillis();
	
	
	@Autowired
    private ApplicationContext applicationContext;
	private AppController controller;
	private User user;


	private ModelMap model = new ModelMap();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Before
	public void setUp() throws Exception {
		this.controller = this.applicationContext.getBean(
				AppController.class
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

	@Test
	public void testInitBinder() {
		fail("Not yet implemented");
	}

	@Test
	public void testListUsers() {
		this.controller.listUsers(this.model);
		assertTrue(this.model.containsAttribute("users"));
		assertTrue(this.model.containsAttribute("loggedinuser"));
	}

	@Test
	public void testNewUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUserUserBindingResultModelMapString() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testInitializeProfiles() {
		fail("Not yet implemented");
	}

	@Test
	public void testAccessDeniedPage() {
		this.controller.accessDeniedPage(model);
	}

	@Test
	public void testLoginPage() {
		String loginPage = this.controller.loginPage();
		assertEquals("redirect:/list", loginPage);
		// TODO also test not logged in
	}

	@Test
	public void testLogoutPage() {
		HttpServletResponse response = new MockHttpServletResponse();
		HttpServletRequest request = new MockHttpServletRequest();
		String page = this.controller.logoutPage(request , response );
		assertEquals("redirect:/login?logout", page);
		
		//TODO now check can't edit
		//TODO now log in
	}

	@Test
	public void testListPro() {
		this.controller.listPro(this.model );
		assertNotNull(model.containsAttribute("projects"));
	}

	@Test
	public void testListData() {
		try {
			this.controller.listData(-1L, this.model);
		} catch (NullPointerException e) {
			// TODO doesn't seem right, unless response code is 404
		}
	}

	@Test
	public void testAddDataset() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddDatasetPost() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFiless() {
		fail("Not yet implemented");
	}

	@Test
	public void testUploadFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testNewDataset() {
		fail("Not yet implemented");
	}

	@Test
	public void testNewProject() {
		fail("Not yet implemented");
		// TODO 		this.controller.deleteDataSet(-1L, -1L);
	}

	@Test
	public void testSaveProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateTarFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteFileNonesuch() {
		try {
			this.controller.deleteFile(-1,  -1L);
		} catch (IllegalArgumentException e) {
			// that's OK I guess, will the response code be appropriate?
		}
	}

	@Test
	public void testEditProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUserProjectBindingResultModelMapLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteProjectNonesuch() {
		try {
			this.controller.deleteProject(-1L);
		} catch (IllegalArgumentException e) {
			// that's OK, if the response code is appropriate
		}
	}



}
