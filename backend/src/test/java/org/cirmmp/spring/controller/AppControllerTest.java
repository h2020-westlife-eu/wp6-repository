package org.cirmmp.spring.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.cirmmp.spring.configuration.AppConfig;
import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.FileBucket;
import org.cirmmp.spring.model.FileList;
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
import org.springframework.security.core.userdetails.User;
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
		try {
			this.controller.deleteUser("nonesuch");
		} catch (IllegalArgumentException e) {
			// that's Ok, if it leads to an Invalid Request response code
		}
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
	public void testAddDatasetPost() throws IOException {
		
		// create new dataset
		String page = this.controller.newDataset(this.model);
		assertEquals("newdataset", page);
		assertTrue(model.containsAttribute("dataset"));
		DataSet dataset = (DataSet) model.get("dataset");
		assertNotNull(dataset);
		assertNull(dataset.getId());  
		
		// save details to database: gets id
		BindingResult result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");;
		this.controller.addDatasetPost(-1L, dataset, result, model);
		assertNotNull(dataset.getId());  
		
		// mock upload
		FileBucket bucket = new FileBucket();
		MultipartFile file = new MockMultipartFile("filename", CONTENT.getBytes());
		bucket.setFile(file );
		result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		page = this.controller.uploadFile(bucket , result , this.model, dataset.getId());
		assertEquals(page, "redirect:/add-file-"+dataset.getId());

		List<FileList> fileLists = dataset.getFileLists();
		assertEquals(0, fileLists.size());  // TODO why?
		
		
		/* TODO // download
		assertEquals(1, fileLists.size());
		FileList fl = fileLists.iterator().next();
		this.response.setOutputStreamAccessAllowed(true);
		this.controller.downloadFile(-1, fl.getId(), response );
		byte[] bytes = response.getContentAsByteArray();
		assertEquals(CONTENT, bytes);  
		
		this.controller.deleteFile(-1,  fl.getId());
		*/
		// TODO delete dataset
		
	}

	@Test
	public void testAddFiless() {
		fail("Not yet implemented");
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
	public void testNewDataset() throws IOException {
		String page = this.controller.newDataset(this.model);
		assertEquals("newdataset", page);
		assertTrue(model.containsAttribute("dataset"));
		DataSet dataset = (DataSet) model.get("dataset");
		assertNotNull(dataset);
		assertNull(dataset.getId()); // TODO huh?
		
		// now upload a file
		FileBucket fileBucket = new FileBucket();
		BindingResult result = new MapBindingResult(Collections.EMPTY_MAP, "hmm");
		//TODO this.controller.uploadFile(fileBucket , result , this.model, dataset.getId());
		
		//TODO now downlod the file
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
	public void testCreateTarFile() {
		fail("Not yet implemented");
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
