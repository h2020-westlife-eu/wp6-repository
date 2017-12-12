package org.cirmmp.spring.controller;


import com.google.gson.Gson;
import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.model.Project;
import org.cirmmp.spring.model.UploadModel;
import org.cirmmp.spring.model.User;
import org.cirmmp.spring.model.json.JFileList;
import org.cirmmp.spring.model.json.JProject;
import org.cirmmp.spring.service.FileListService;
import org.cirmmp.spring.model.UserProfile;
import org.cirmmp.spring.service.ProjectService;
import org.cirmmp.spring.service.UserProfileService;
import org.cirmmp.spring.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restcon")
public class RestCon {
    private static final Logger LOG = LoggerFactory.getLogger(RestCon.class);
    private static Gson gson = new Gson();

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    FileListService fileListService;

    @Autowired
    UserProfileService userProfileService;

    //@Autowired
    //LinkService linkService;

    //@Autowired
    //OfferService offerService;

    //@Autowired
    //OrdiniService ordiniService;

    @RequestMapping(value = { "/user" },method = RequestMethod.GET)
    public ResponseEntity listOrder(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname){
        String username = checkAuthentication(xusername);
        LOG.info("sono in user");
        LOG.info("user from HTTP header (westlife-sso):"+xusername);

        //return "Welcome, " + username;

        //List<Offer> offers = offerService.findAllOffer();
        //String jsonOffers = gson.toJson(username);
        LOG.info("JSON OFFERS");
        LOG.info(username);
        return new ResponseEntity (gson.toJson(username), HttpStatus.OK);
    }

    public class UserDTO {
      String FirstName;
      String LastName;
      String Email;
      String Id;
      String SsoId;
    }

    /* return list of users registered inside repository DB*/
    @RequestMapping(value = { "/users" },method = RequestMethod.GET)
    public ResponseEntity listUsers(){
        //String username = checkAuthentication(xusername);
        //LOG.info("sono in user");
        //LOG.info("user from HTTP header (westlife-sso):"+xusername);

        //return "Welcome, " + username;

        //List<Offer> offers = offerService.findAllOffer();
        //String jsonOffers = gson.toJson(username);
        //LOG.info("JSON OFFERS");
        //LOG.info(username);
        List<User> users = userService.findAllUsers();
        List<UserDTO> userdtos = new ArrayList<UserDTO>();
        for (Iterator<User> it=users.iterator(); it.hasNext(); ){
          User user = it.next();
          UserDTO udto= new UserDTO();
          udto.FirstName = user.getFirstName();
          udto.LastName = user.getLastName();
          udto.Email = user.getEmail();
          udto.Id = user.getId().toString();
          udto.SsoId = user.getSsoId();
          userdtos.add(udto);
        }

        return new ResponseEntity (gson.toJson(userdtos), HttpStatus.OK);
    }


    @RequestMapping(value = {"/createProject","/project"}, method=RequestMethod.POST)
    public ResponseEntity createProject(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        LOG.info("sono in createproject");

        String ssoId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findBySSO(ssoId);
        Date now = new Date();
        Project project = new Project();
        project.setCreation_date(now);
        project.setUserId(user.getId());
        project.setProjectName("Test");
        project.setSummary("TEST TEST TEST");
        LOG.info("sono in createproject 2");
        projectService.save(project);
        return new ResponseEntity(gson.toJson(project), HttpStatus.OK);
    }

    @RequestMapping(value = "/createProject", method = RequestMethod.POST,consumes = {"application/json"},produces = {"application/json"})
    public ResponseEntity createProject(@RequestBody JProject jProject){
        String ssoId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findBySSO(ssoId);
        Date now = new Date();
        Project project = new Project();
        project.setCreation_date(now);
        project.setUserId(user.getId());
        project.setProjectName(jProject.getProjectName());
        project.setSummary(jProject.getSummary());
        projectService.save(project);
        return new ResponseEntity(project, HttpStatus.OK);
    }

    @RequestMapping(value = {"/listProject", "/project"}, method=RequestMethod.GET)
    public ResponseEntity listProject(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        LOG.info("sono in createproject");
        String ssoId = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Project> projects =projectService.findByUserId(user.getId());


        return new ResponseEntity(projects, HttpStatus.OK);
    }

    private class PostData {
        public String access_token;
        public String aria_response_format;
    }

    //updates project from ARIA API
    @RequestMapping(value = {"/updateProject"}, method=RequestMethod.POST)
    public ResponseEntity updateProject(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        /*Project project = new Project();
        project.setUserId(user.getId());
        project.setProjectName("Test");
        project.setSummary("TEST TEST TEST");
        LOG.info("sono in createproject 2");

        projectService.save(project);*/

        Project [] projects;
        String uri= "https://www.structuralbiology.eu/ws/oauth/proposallist";
        RestTemplate rt = new RestTemplate();
        PostData data = new PostData();data.access_token="";data.aria_response_format="json";

        String result = rt.postForObject(uri,data,String.class);
        LOG.info("updated list of projects:"+result);

        return new ResponseEntity(gson.toJson(result), HttpStatus.OK);
    }



    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    private String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


    private User createSSOUser(@RequestHeader(name = "X-USERNAME", defaultValue = "") String xusername, @RequestHeader(name = "X-NAME", defaultValue = "") String xname, @RequestHeader(name = "X-EMAIL", defaultValue = "") String xemail, String ssoId, @RequestHeader(name = "X-GROUPS", defaultValue = "")String xgroups) {
        User user;//user doesn't exist in local system yet, create it from SSO West-Life information
        user = new User();
        user.setSsoId(xusername);
        String[] names = xname.split(" ");
        user.setFirstName(getFirstNames(names)); //first name in names, or first two names "Jose" or "Jose Maria"?
        user.setLastName(names[names.length-1]); //last name or last names "Maria Carazo" or "Carazo"
        user.setEmail(xemail);
        //TODO: disable password - or generate random
        user.setPassword(randomString(30));


        //TODO where to put user groups? issue #10
        //if (xgroups contains 'West-Life' or 'ARIA') user.setUserProfiles('USER')
        //if (xusername == 'admin eppn' user.setUserProfile('ADMIN')...
        //now everybody logged via West-Life SSO is USER
        HashSet<UserProfile> ups = new HashSet<>();
        ups.add(userProfileService.findByType("USER"));
        user.setUserProfiles(ups);

        LOG.info("creating user:"+user.toString());
        LOG.info("groups:"+xgroups);
        userService.saveUser(user);
        user = userService.findBySSO(ssoId);
        //should have the user.getId() set now
        return user;
    }

    //returns all names except last one delimited by space;
    private String getFirstNames(String[] names){
        StringBuffer s = new StringBuffer();
        for (int i=0;i<names.length-1;i++)
        {
            if (i>0) s.append(" ");
            s.append(names[i]);
        }
        return s.toString();
    }
    public String checkAuthentication(String xusername) {
        LOG.info("checkAuthentication()");
        //String xusername="";
        String ssoId = (xusername.length()>0)? xusername: SecurityContextHolder.getContext().getAuthentication().getName();
        if (ssoId=="") {
            LOG.info("checkAuthentication: not authenticated");
            throw new UnauthorizedUserException("authorization required");
            //new ResponseEntity("authorization required", HttpStatus.UNAUTHORIZED);
        } else {
            return ssoId;
        }
    }
    public User checkAuthentication(String xusername,String xname,String xemail,String xgroups){
        LOG.info("checkAuthentication()");
        //String xusername="";
        String ssoId = (xusername.length()>0)? xusername: SecurityContextHolder.getContext().getAuthentication().getName();
        if (ssoId=="") {
            LOG.info("checkAuthentication: not authenticated");
            throw new AuthenticationCredentialsNotFoundException("authorization required");

            //new ResponseEntity("authorization required", HttpStatus.UNAUTHORIZED);
        } else {
            User user= userService.findBySSO(ssoId);
            //fix issue #9
            if (user==null) {
                if (xusername.length()>0)
                    user = createSSOUser(xusername, xname, xemail, ssoId, xgroups);
                else {//return http 401
                    LOG.info("checkAuthentication: not authenticated");
                    throw new AuthenticationCredentialsNotFoundException("authorization required");
                }
            }
            LOG.info("checkAuthentication: OK");
            return user;
        }
    }

    @RequestMapping(value = { "/upload" },method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) {

        LOG.debug("Single file upload!");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfile));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }

    // 3.1.2 Multiple file upload
    @RequestMapping(value = {"/upload/multi"}, method = RequestMethod.POST)
    public ResponseEntity<?> uploadFileMulti(
            @RequestParam("extraField") String extraField,
            @RequestParam("files") MultipartFile[] uploadfiles) {

        LOG.debug("Multiple file upload!");

        // Get file name
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfiles));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - "
                + uploadedFileName, HttpStatus.OK);

    }

    // 3.1.3 maps html form to a Model
    @RequestMapping(value = {"/upload/multi/model"}, method = RequestMethod.POST)
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute JFileList model) {

        LOG.debug("Multiple file upload! With UploadModel");

        try {

            saveUploadedFiles(Arrays.asList(model.getFiles()));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        FileList fileList = new FileList();
        fileList.setFileInfo(model.getFileInfo());
        fileList.setProjectId(model.getProjectId());
        fileList.setFiletName(model.getFiletName());
        fileListService.save(fileList);

        return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);

    }


    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/tmp/";

    //save file
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        }

    }
}
