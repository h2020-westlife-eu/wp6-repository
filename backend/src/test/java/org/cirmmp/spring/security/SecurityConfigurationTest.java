package org.cirmmp.spring.security;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.cirmmp.spring.configuration.AppConfig;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes={ AppConfig.class }
)
@WebAppConfiguration
public class SecurityConfigurationTest {


	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testCanGetLoginForm() throws Exception {
		this.mvc.perform(get("/login")).andExpect(MockMvcResultMatchers.status().isOk());
	}


	@Test
	public void testBadCsrf() throws Exception {
		ResultActions result = this.mvc
		.perform(post("/login").with(csrf().useInvalidToken()));
		result.andExpect(redirectedUrl("/login?error" ));
		// could result.andExpect(MockMvcResultMatchers.status().is(403));
	}

	@Test
	public void testNotLoggedInGet() throws Exception {
		this.mvc
		.perform(get("/newuser"))
		.andExpect(redirectedUrl("http://localhost/login"));
	}
	

	@Test
	public void testUnknownGet() throws Exception {
		this.mvc
		.perform(get("/newuser").with(user("testuser")))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	
	@Test
	public void testNotLoggedInPost() throws Exception {
		this.mvc
		.perform(post("/newuser").with(csrf()))
		.andExpect(redirectedUrl("http://localhost/login")); 
			}

	@Test
	public void testNotFound() throws Exception {
		this.mvc.perform(get("/nonesuch")).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test 
	public void testLoginNonesuch() throws Exception {
		this.mvc
		.perform( post("/login").param("ssoId", "nonesuch").param("password", "").with(csrf()) )
		.andExpect(redirectedUrl("/login?error")); 
			
	}
	


    // TODO test successful login
	// TODO check non-admin user cannot use admin functionality
}
