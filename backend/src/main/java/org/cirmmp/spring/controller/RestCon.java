package org.cirmmp.spring.controller;


import com.google.gson.Gson;
import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.model.Project;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;

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

/*    @RequestMapping(value = { "/user" },method = RequestMethod.GET)
    public ResponseEntity listOrder(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        //User user = userService.findBySSO(username);

        return new ResponseEntity (gson.toJson(getUserDTO(user)), HttpStatus.OK);
    }
*/

    @RequestMapping(value = { "/user" },method = RequestMethod.GET,produces = {"application/json"})
    public ResponseEntity listOrder() {
        LOG.info("sono in user");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //return "Welcome, " + username;
        Gson gson = new Gson();
        //List<Offer> offers = offerService.findAllOffer();
        //String jsonOffers = gson.toJson(username);
        LOG.info("JSON OFFERS");
        LOG.info(username);
        return new ResponseEntity(username, HttpStatus.OK);
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
        List<User> users = userService.findAllUsers();
        List<UserDTO> userdtos = new ArrayList<UserDTO>();
        for (Iterator<User> it=users.iterator(); it.hasNext(); ){
          User user = it.next();
          userdtos.add(getUserDTO(user));
        }

        return new ResponseEntity (gson.toJson(userdtos), HttpStatus.OK);
    }

    private UserDTO getUserDTO(User user) {
        UserDTO udto= new UserDTO();
        udto.FirstName = user.getFirstName();
        udto.LastName = user.getLastName();
        udto.Email = user.getEmail();
        udto.Id = user.getId().toString();
        udto.SsoId = user.getSsoId();
        return udto;
    }


    @RequestMapping(value = {"/createProject","/project"}, method=RequestMethod.POST)
    public ResponseEntity createProject(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        LOG.info("sono in createproject");

        //String ssoId = SecurityContextHolder.getContext().getAuthentication().getName();
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

    @RequestMapping(value = "/listProject")
    public ResponseEntity listProject(){
        LOG.info("sono in createproject");
        String ssoId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findBySSO(ssoId);
        List<Project> projects =projectService.findByUserId(user.getId());
        return new ResponseEntity(projects, HttpStatus.OK);
    }

//    @RequestMapping(value = { "/filelist-{projectId}" }, method = RequestMethod.GET)
//    public ResponseEntity listFilesId(@PathVariable Long projectId) {
//
//        Project project = projectService.findById(projectId);
//        List<FileList> files = project.getFileLists();
//        ArrayList<JFileList> nfiles = new ArrayList<>();
//        for(FileList ifile :files){
//            JFileList infiles = new JFileList();
//            infiles.setFiletName(ifile.getFileName());
//            infiles.setFileInfo(ifile.getFileInfo());
//           // infiles.setProjectId(ifile.getProjectId());
//            nfiles.add(infiles);
//        }
//
//        return new ResponseEntity(nfiles, HttpStatus.OK);
//    }

    @RequestMapping(value = { "/filelist" }, method = RequestMethod.GET)
    public ResponseEntity listFiles() {

        List<FileList> files = fileListService.findAllFiles();
        ArrayList<JFileList> nfiles = new ArrayList<>();
        for(FileList ifile :files){
            JFileList infiles = new JFileList();
            infiles.setFiletName(ifile.getFileName());
            infiles.setFileInfo(ifile.getFileInfo());
            //infiles.setProjectId(ifile.getProjectId());
            infiles.setCreation_date(ifile.getCreation_date());
            infiles.setSummary(infiles.getSummary());
            infiles.setWebdavurl(infiles.getWebdavurl());
            nfiles.add(infiles);
        }
        return new ResponseEntity(nfiles, HttpStatus.OK);
    }

    /** utils to create user in database if it is logged using SSO and not in DB yet
     *
     */
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
    //checks authentication, either it is authenticated via spring - or via sent arguments x*
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

}
