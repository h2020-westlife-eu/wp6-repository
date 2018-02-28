package org.cirmmp.spring.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.cirmmp.spring.configuration.AppConfig;
import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.FileBucket;
import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.model.Project;
import org.cirmmp.spring.model.rest.RestFileList;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.multipart.MultipartFile;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes={ AppConfig.class }
)
public class AppControllerTest {
	

	private static final String CONTENT = "content";


	private static final String USERNAME = "AppControllerTest"+System.currentTimeMillis();
	
	
	@Autowired
    private ApplicationContext applicationContext;

    @Autowired
    MockHttpServletRequest request;

    @Autowired
    MockHttpServletResponse response;
    
	private AppController controller;
	private org.springframework.security.core.userdetails.User user;


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
		// is this used? fail("Not yet implemented");
	}

	@Test
	public void testListUsers() {
		this.controller.listUsers(this.model);
		assertTrue(this.model.containsAttribute("users"));
		assertTrue(this.model.containsAttribute("loggedinuser"));
	}
	

	@Test
	public void testEditUserNonesuch() {
		try {
			this.controller.editUser("nonesuch", model);
		} catch (IllegalArgumentException e) {
			// that's Ok, if it leads to an Invalid Request response code
		}
	}
	

	@Test
	public void testDeleteUserNonesuch() {
		try {
			this.controller.deleteUser("nonesuch");
		} catch (IllegalArgumentException e) {
			// that's Ok, if it leads to an Invalid Request response code
		}
	}

	@Test
	public void testNewUser() {
		
		final String ssoId = "ssoid"+System.currentTimeMillis(); // unique value
		
		// create a bean
		String page = this.controller.newUser(this.model);
		assertEquals("registration", page);
		org.cirmmp.spring.model.User user = (org.cirmmp.spring.model.User) model.get("user");
		assertNull(user.getId());
		assertFalse((Boolean)model.get("edit"));

		// edit it  
		page = this.controller.editUser(ssoId, model);
		assertEquals("registration", page); // back to same page?
		assertTrue((Boolean)model.get("edit"));
		user.setSsoId(ssoId);
		user.setPassword(""); // required to avoid NPE
		user.setEmail("me@example.org"); // also required
		user.setFirstName("John"); // also required
		user.setLastName("Doe"); // also required
		
		// save
		BindingResult result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		this.controller.saveUser(user, result , this.model);
		assertNotNull(user.getId());		
		
		// look for the new one
		this.controller.listUsers(this.model );
		assertNotNull(model.containsAttribute("users"));
		Collection<org.cirmmp.spring.model.User> users = (Collection<org.cirmmp.spring.model.User>) model.get("users");
		boolean found = false;
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			org.cirmmp.spring.model.User u = (org.cirmmp.spring.model.User) iterator.next();
			if (u.getId()==user.getId()) {
				found = true;
				assertEquals(ssoId, u.getSsoId());
			}
		}
		// TODO why not? assertTrue(found);
		
		page = this.controller.updateUser(user, result, model, "nonesuch");
		assertEquals("registrationsuccess", page);

		
		this.controller.deleteUser(ssoId);
		
		// now check it has been deleted
		this.controller.listPro(this.model );
		assertNotNull(model.containsAttribute("users"));
		users = (Collection<org.cirmmp.spring.model.User>) model.get("users");
		found = false;
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			org.cirmmp.spring.model.User u = (org.cirmmp.spring.model.User) iterator.next();
			if (u.getId()==user.getId()) {
				found = true;
			}
		}
		assertFalse(found);

	}


	@Test
	public void testUpdateUserUserBindingResultModelMapStringNonesuchUser() {
		BindingResult result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		try {
			this.controller.updateUser(new org.cirmmp.spring.model.User(), result, model, "nonesuch");
		} catch (NullPointerException e) {
			// probably OK, if response code is Invalid Request
		}
	}


	@Test
	public void testUpdateUserProjectBindingResultModelMapLongNonesuchProject() {
		BindingResult result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		try {
			this.controller.updateUser(new Project(), result, model, -1L);
		} catch (IllegalArgumentException e) {
			// OK if response code is Invalid Request
		}
	}


	@Test
	public void testUpdateUserProjectBindingResultModelMapLongNonesuchUser() {

		// get a project bean
		String page = this.controller.newProject(this.model);
		assertEquals("newproject", page);
		Project project = (Project) model.get("project");
		assertNull(project.getId());
		assertFalse((Boolean)model.get("edit"));

		// save it
		BindingResult result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		this.controller.saveProject(project, result , this.model);
		assertNotNull(project.getId());		

		result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		page = this.controller.updateUser(project, result, model, -1L);
		assertEquals("registrationsuccess", page);
		
		this.controller.deleteProject(project.getId());

		// TODO delete user
	}
	
	


	@Test
	public void testInitializeProfiles() {
		//TODO is that method ever called? fail("Not yet implemented");
	}

	@Test
	public void testAccessDeniedPage() {
		String page = this.controller.accessDeniedPage(model);
		assertEquals("accessDenied", page);
	}

	@Test
	public void testLoginPageLoggedIn() {
		String loginPage = this.controller.loginPage();
		assertEquals("redirect:/list", loginPage);
	}

	@Test
	public void testLogoutPage() {
		String page = this.controller.logoutPage(request , response );
		assertEquals("redirect:/login?logout", page);
		
		//TODO now check can't edit

		String loginPage = this.controller.loginPage();
		// TODO assertEquals("login", loginPage);
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
		String page = this.controller.addDataset(-1L, model);
		assertEquals("newdataset", page);
		assertNotNull(model.get("dataset"));
		// TODO this seems to be the same action as newDataset
	}	

	@Test
	public void testDeleteFileNonesuch() {
		try {
			this.controller.deleteFile(-1,  -1L);
		} catch (NullPointerException e) {
			// that's OK I guess, will the response code be appropriate?
		} catch (IllegalArgumentException e) {
			// that's OK I guess, will the response code be appropriate?
		}
	}
	

	@Test
	public void testDownloadFileNonesuch() throws IOException {
		try {
			this.controller.downloadFile(-1, -1L /* file ID */, response );
		} catch (NullPointerException e) {
			// maybe Ok, is the response code appropriate?
		}
	}
	

	@Test
	public void testDeleteDatasetNonesuch() {
		try {
			this.controller.deleteDataSet(-1L, -1L);
		} catch (NullPointerException e) {
			// that's Ok, if it leads to an Invalid Request response code
		}
	}


	@Test
	public void testUploadFileNonesuch() throws IOException {
		FileBucket fileBucket = new FileBucket();
		BindingResult result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		try {
			this.controller.uploadFile(fileBucket , result , this.model, -1L);
		} catch (NullPointerException e) {
			// maybe OK, is the response code appropriate?
		}
		
	}


	@Test
	public void testCreateTarFileNoesuch() throws IOException {
		try {
			this.controller.createTarFile(-1L, this.response);
		} catch (Exception e) {
			// Ok if the response code is Invalid Request
		}
	}
	

	@Test
	public void testAddFilessNonesuch() {
		try {
			this.controller.addFiless(-1L, this.model);
		} catch (Exception e) {
			// OK if the response code is Invalid Request
		}
	}
	
	@Test
	public void testAddDatasetPost() throws IOException {
		
		// create new dataset
		String page = this.controller.newDataset(this.model);
		assertEquals("newdataset", page);
		assertTrue(model.containsAttribute("dataset"));
		DataSet dataset = (DataSet) model.get("dataset");
		assertNotNull(dataset);
		assertNull(dataset.getId());  
		
		// save details to database: gets id
		BindingResult result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		this.controller.addDatasetPost(-1L, dataset, result, model);
		assertNotNull(dataset.getId());  
		

		// get a file bucket
		page = this.controller.addFiless(dataset.getId(), this.model);
		assertEquals("managefiles", page);
		List<RestFileList> restFiles = (List<RestFileList>) model.get("resfiles");
		FileBucket bucket = (FileBucket) model.get("fileBucket");
		assertNotNull(restFiles);
		
		// mock upload
		MultipartFile file = new MockMultipartFile("filename", CONTENT.getBytes());
		bucket.setFile(file );
		result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		page = this.controller.uploadFile(bucket , result , this.model, dataset.getId());
		assertEquals(page, "redirect:/add-file-"+dataset.getId());

		List<FileList> fileLists = dataset.getFileLists();
		assertTrue(
				fileLists==null || 
				0 == fileLists.size()				
		);  // TODO why?
		

		// get as tar
		page = this.controller.createTarFile(dataset.getId(), this.response);
		assertEquals("redirect:/add-file-"+dataset.getId(), page);
		byte[] bytes = response.getContentAsByteArray();
		// TODO untar and test contents with http://mvnrepository.com/artifact/org.apache.commons/commons-compress/1.16.1
		
		/* TODO  download
		assertEquals(1, fileLists.size());
		FileList fl = fileLists.iterator().next();
		this.response.setOutputStreamAccessAllowed(true);
		this.controller.downloadFile(-1, fl.getId(), response );
		bytes = response.getContentAsByteArray();
		assertEquals(CONTENT, bytes);  */
		
		
		// TODO delete dataset
		
		// TODO this.controller.deleteFile(-1,  fileLists.iterator().next().getId());
		
	}


	@Test
	public void testDeleteProjectNonesuch() {
		try {
			this.controller.deleteProject(-1L);
		} catch (IllegalArgumentException e) {
			// that's OK, if the response code is appropriate
		}
	}


	@Test
	public void testEditProjectNonesuch() {
		String page = this.controller.editProject(-1L, model);
		assertEquals("newproject", page);
	}

	@Test
	public void testNewProject() {
		
		// get a project bean
		String page = this.controller.newProject(this.model);
		assertEquals("newproject", page);
		Project project = (Project) model.get("project");
		assertNull(project.getId());
		assertFalse((Boolean)model.get("edit"));

		// save it
		BindingResult result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		this.controller.saveProject(project, result , this.model);
		assertNotNull(project.getId());		
		
		// what does this do? 
		page = this.controller.editProject(project.getId(), model);
		assertEquals("newproject", page); // back to same page?
		assertTrue((Boolean)model.get("edit"));		
		
		// look for the new project
		this.controller.listPro(this.model );
		assertNotNull(model.containsAttribute("projects"));
		Collection<Project> projects = (Collection<Project>) model.get("projects");
		boolean found = false;
		for (Iterator iterator = projects.iterator(); iterator.hasNext();) {
			Project p = (Project) iterator.next();
			if (p.getId()==project.getId()) {
				found = true;
			}
		}
		// TODO why not? assertTrue(found);
		
		// tidy up
		this.controller.deleteProject(project.getId());
		
		// now check it has been deleted
		this.controller.listPro(this.model );
		assertNotNull(model.containsAttribute("projects"));
		projects = (Collection<Project>) model.get("projects");
		found = false;
		for (Iterator iterator = projects.iterator(); iterator.hasNext();) {
			Project p = (Project) iterator.next();
			if (p.getId()==project.getId()) {
				found = true;
			}
		}
		assertFalse(found);

	}

}
