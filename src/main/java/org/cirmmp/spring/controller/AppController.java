package org.cirmmp.spring.controller;

import org.cirmmp.spring.model.*;
import org.cirmmp.spring.service.FileListService;
import org.cirmmp.spring.service.ProjectService;
import org.cirmmp.spring.service.UserProfileService;
import org.cirmmp.spring.service.UserService;
import org.cirmmp.spring.util.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
    MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	@Autowired
	ProjectService projectService;

	@Autowired
	FileListService fileListService;

	//@Autowired
	//LinkService linkService;

	//@Autowired
	//OfferService offerService;

	//@Autowired
	//OrdiniService ordiniService;

	@Autowired
	FileValidator fileValidator;

	@InitBinder("fileBucket")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(fileValidator);
	}


    private static final Logger logger = LoggerFactory
            .getLogger(AppController.class);
	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		return "userslist";
	}

	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result,
                           ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
		 * and applying it on field [sso] of Model class [User].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}
		
		userService.saveUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		//return "success";
		return "registrationsuccess";
	}


	/**
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String ssoId, ModelMap model) {
		User user = userService.findBySSO(ssoId);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}
	
	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String ssoId) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}*/


		userService.updateUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registrationsuccess";
	}

	
	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String ssoId) {
		userService.deleteUserBySSO(ssoId);
		return "redirect:/list";
	}
	

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}
	
	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}

	/**
	 * This method handles login GET requests.
	 * If users is already logged-in and tries to goto login page again, will be redirected to list page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
	    } else {
	    	return "redirect:/list";  
	    }
	}

	/**
	 * This method handles logout requests.
	 * Toggle the handlers if you are RememberMe functionality is useless in your app.
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			//new SecurityContextLogoutHandler().logout(request, response, auth);
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	/**
	 * This method returns true if users is already authenticated [logged-in], else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authenticationTrustResolver.isAnonymous(authentication);
	}


	@RequestMapping(value = { "/listPro" }, method = RequestMethod.GET)
	public String listPro(ModelMap model) {

		List<Project> projects = projectService.findAllProject();
		model.addAttribute("projects", projects);
		return "projectList";
	}

	@RequestMapping(value = { "/add-file-{projectId}" }, method = RequestMethod.GET)
	public String addFiless(@PathVariable int projectId, ModelMap model) {

        logger.info("Sono in add-file GET");
		Project project = projectService.findById(projectId);
		model.addAttribute("project", project);

		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);

		List<FileList> files = fileListService.findByProjectId(projectId);
		model.addAttribute("files", files);

		return "managefiles";
	}

	@RequestMapping(value = { "/add-file-{projectId}" }, method = RequestMethod.POST)
	public String uploadFile(@Valid FileBucket fileBucket, BindingResult result, ModelMap model, @PathVariable int projectId) throws IOException {

        logger.info("Sono in add-file POST");

		if (result.hasErrors()) {
			System.out.println("validation errors");
			Project project = projectService.findById(projectId);
			model.addAttribute("project", project);

			List<FileList> files = fileListService.findByProjectId(projectId);
			model.addAttribute("files", files);

			return "managefiles";
		} else {

			System.out.println("Fetching file");

			Project project = projectService.findById(projectId);
			model.addAttribute("project", project);

			saveFile(fileBucket, project);

			return "redirect:/add-file-" + projectId;
		}
	}


	@RequestMapping(value = { "/newproject" }, method = RequestMethod.GET)
	public String newProject(ModelMap model) {
		Project project = new Project();
		model.addAttribute("project", project);
		model.addAttribute("edit", false);
		return "newproject";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newproject" }, method = RequestMethod.POST)
	public String saveProject(@Valid Project project, BindingResult result,
						   ModelMap model) {

		if (result.hasErrors()) {
			return "newproject";
		}

		/*
		 * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation
		 * and applying it on field [sso] of Model class [User].
		 *
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 *
		 */
		//if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
		//	FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
		//	result.addError(ssoError);
		//	return "registration";
		//}

		projectService.save(project);

		model.addAttribute("project", project);
		model.addAttribute("success", "Pooject " + project.getProjectName() + " registered successfully");
		//return "success";
		return "registrationsuccessproject";
	}

    @RequestMapping(value = { "/download-file-{prId}-{fileId}" }, method = RequestMethod.GET)
    public String downloadFile(@PathVariable int prId, @PathVariable int fileId, HttpServletResponse response) throws IOException {
        FileList document = fileListService.findById(fileId);
        response.setContentType(document.getType());
        response.setContentLength(document.getContent().length);
        response.setHeader("Content-Disposition","attachment; filename=\"" + document.getFileName() +"\"");

        FileCopyUtils.copy(document.getContent(), response.getOutputStream());

        return "redirect:/add-file-"+prId;
    }

    @RequestMapping(value = { "/delete-file-{prId}-{fileId}" }, method = RequestMethod.GET)
    public String deleteFile(@PathVariable int prId, @PathVariable int fileId) {
        fileListService.deleteById(fileId);
        return "redirect:/add-file-"+prId;
    }



    @RequestMapping(value = { "/edit-project-{Id}" }, method = RequestMethod.GET)
    public String editProject(@PathVariable int Id, ModelMap model) {
        Project project = projectService.findById(Id);
        model.addAttribute("project", project);
        model.addAttribute("edit", true);
        return "registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-project-{Id}" }, method = RequestMethod.POST)
    public String updateUser(@Valid Project project, BindingResult result,
                             ModelMap model, @PathVariable int Id) {

        if (result.hasErrors()) {
            return "registration";
        }

        projectService.updateProject(project);

        model.addAttribute("success", "User " + project.getProjectName() + " updated successfully");
        return "registrationsuccess";
    }


    /**
     * This method will delete an user by it's SSOID value.
     */
    @RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
    public String deletePRoject(@PathVariable String ssoId) {
        userService.deleteUserBySSO(ssoId);
        return "redirect:/list";
    }


	private void saveFile(FileBucket fileBucket, Project project) throws IOException {

		FileList document = new FileList();

		MultipartFile multipartFile = fileBucket.getFile();

		document.setFileName(multipartFile.getOriginalFilename());
		document.setFileInfo(fileBucket.getDescription());
		document.setType(multipartFile.getContentType());
		document.setContent(multipartFile.getBytes());
		document.setProjectId(project.getId());
		fileListService.save(document);
	}

}