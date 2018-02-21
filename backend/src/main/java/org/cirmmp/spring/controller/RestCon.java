package org.cirmmp.spring.controller;


import com.google.gson.Gson;
import org.cirmmp.spring.model.*;
import org.cirmmp.spring.model.json.JFileList;
import org.cirmmp.spring.model.json.JProject;
import org.cirmmp.spring.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.*;

/*TODO 1. consider refactoring the class - extract methods handling /user /project /dataset /[others] to controller service class
   GET (=read)
   PUT (=update),
   POST (=create,or update - if id is presented),
   DELETE (delete)
  TODO 2. refactor the class - extract methods handling projectService operation to new controller class
  - GET on /project = current /listPro
  - GET on /project/{id} = project detail = current /list-data-{} with list of data
  - POST on /project/{id} = adds new dataset, current /add-dataset-{projectid},
  - POST on /project = create new project,
  TODO 3. analogy for /dataset
  - everything is under /dataset (HTTP methods GET,POST,PUT,DELETE)
  - new method /dataset - returns all datasets belonging to user,
  TODO 4.extract authorization checks  methods to new controller class
  TODO 5.new services for utils
  - get tar.gz or zip stream of the dataset
  - stream selected dataset/folder as PUT request to webdav endpoint
*/
@RestController
@RequestMapping("/restcon")
public class RestCon {
    private static final Logger LOG = LoggerFactory.getLogger(RestCon.class);
    private static Gson gson = new Gson();

    @Autowired
    UserService userService;

    @Autowired
    WebDAVCopyUtils webDAVCopyUtils;

    /*try {

        webDAVCopyUtils.copyFromWebDAV("pippo", "pluto");
    } catch (IOException e){
        return new ResponseEntity (gson.toJson(userdtos), HttpStatus.OK);

    }*/

    @Autowired
    ProjectService projectService;

    @Autowired
    FileListService fileListService;

    @Autowired
    DataSetService dataSetService;

    @Autowired
    UserProfileService userProfileService;

    /* returns spring authenticated as well as SSO authenticated (in http headers)*/
    @RequestMapping(value = { "/user" },method = RequestMethod.GET)
    public ResponseEntity listOrder(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        return new ResponseEntity (gson.toJson(DTOUtils.getUserDTO(user)), HttpStatus.OK);
    }


    /* return list of users registered inside repository DB*/
    @RequestMapping(value = { "/users" },method = RequestMethod.GET)
    public ResponseEntity listUsers(){
        List<User> users = userService.findAllUsers();
        List<UserDTO> userdtos = new ArrayList<UserDTO>();
        for (Iterator<User> it=users.iterator(); it.hasNext(); ){
          User user = it.next();
          userdtos.add(DTOUtils.getUserDTO(user));
        }

        return new ResponseEntity (gson.toJson(userdtos), HttpStatus.OK);
    }


    //'/createproject' and '/listproject' urls violates common HATEOAS of REST - it should be all '/project' - verb is already specified in http request
    // POST = create, PUT=update, GET = list GET/id = project detail, DELETE = delete, application/json - spring doesn't operate it automatically?
    // Does spring framework have some automatic handling of content type - application/json is processed by json, application/xml is proccessed by some xml binding
    // tool?

    @RequestMapping(value = {"/project"}, method=RequestMethod.POST,consumes = {"application/json"},produces = {"application/json"})
    public ResponseEntity createProject(@RequestBody JProject jProject, @RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        //String ssoId = SecurityContextHolder.getContext().getAuthentication().getName();
        Date now = new Date();
        Project project = new Project();
        project.setCreation_date(now);
        project.setUserId(user.getId());
        project.setProjectName(jProject.getProjectName());
        project.setSummary(jProject.getSummary());
        projectService.save(project);
        return new ResponseEntity(gson.toJson(DTOUtils.getProjectDTO(project)), HttpStatus.OK);
    }

    // check using method security interceptor if the user have role USER
    @Secured("USER")
    @RequestMapping(value = "/project", method=RequestMethod.GET)
    public ResponseEntity listProject(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        LOG.info("listProject()");
        String ssoId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Project> projects =projectService.findByUserId(user.getId());
        LOG.info("list projects size:"+projects.size()+" first: "+ ((projects.size()>0) ? (projects.get(0).getProjectName()) : "NA"));
        return new ResponseEntity(gson.toJson(DTOUtils.getProjectDTO(projects)), HttpStatus.OK);
    }

//TODO remove - duplicates /dataset
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

    private User createSSOUser(@RequestHeader(name = "X-USERNAME", defaultValue = "") String xusername, @RequestHeader(name = "X-NAME", defaultValue = "") String xname, @RequestHeader(name = "X-EMAIL", defaultValue = "") String xemail, String ssoId, @RequestHeader(name = "X-GROUPS", defaultValue = "")String xgroups) {
        User user;//user doesn't exist in local system yet, create it from SSO West-Life information
        user = new User();
        user.setSsoId(xusername);
        String[] names = xname.split(" ");
        user.setFirstName(getFirstNames(names)); //first name in names, or first two names "Jose" or "Jose Maria"?
        user.setLastName(names[names.length-1]); //last name or last names "Maria Carazo" or "Carazo"
        user.setEmail(xemail);
        //TODO: disable password - or generate random
        user.setPassword(DTOUtils.randomString(30));


        //TODO where to put user groups? issue #10
        //if (xgroups contains 'West-Life' or 'ARIA') user.setUserProfiles('USER')
        //if (xusername == 'admin eppn' user.setUserProfile('ADMIN')...
        //now everybody logged via West-Life SSO is USER

        //TODO what user profile???? - fails on Column 'USER_PROFILE_ID' cannot be null,
        //'USER' should be in batchSchema.sql
        HashSet<UserProfile> ups = new HashSet();
        UserProfile up = userProfileService.findByType("USER");
        LOG.info("profile id:"+up.getId()+" profile type:"+up.getType());
        ups.add(up);
        user.setUserProfiles(ups);


        LOG.info("creating user in local db from SSO information:"+user.toString());
        LOG.info("groups in contruction now ignored:"+xgroups);
        userService.saveUser(user);
        user = userService.findBySSO(ssoId);
        //should have the user.getId() set now

        //AUTO Authententicate USER
        // Authenticate the user on spring framework after creation
        AutoUser autoUser =new AutoUser();
        autoUser.setFirstName(getFirstNames(names));
        autoUser.setLastName(names[names.length-1]);
        autoUser.setEmail(xemail);
        autoUser.setPassword(randomString(30));
        //chosose from USER ADMIN DBA all role have to be defined defined on SecurityConfiguration
        autoUser.setRole("USER");
        Authentication auth = new UsernamePasswordAuthenticationToken(autoUser,
                autoUser.getPassword(), autoUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
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

    //checks authentication, either it is authenticated via spring - or via sent arguments x*
    //synchronized - multiple calls might be concurrent in - userService.findBySSO(ssoid);
    public synchronized User checkAuthentication(String xusername,String xname,String xemail,String xgroups){
        LOG.info("checkAuthentication()");
        //String xusername="";
        //ssoid is xusername - if set, otherwise ask for context - spring authenticated
        String ssoId = (xusername.length()>0)? xusername: SecurityContextHolder.getContext().getAuthentication().getName();

        //if ssoid is not found or is empty
        if (ssoId=="") {
            LOG.info("checkAuthentication: not authenticated");
            throw new AuthenticationCredentialsNotFoundException("authentication required");
            //new ResponseEntity("authorization required", HttpStatus.UNAUTHORIZED);
        } else {
            //try to find sso in DB - if authenticated by spring - it is there
            LOG.info("authenticated user ssoid:"+ssoId);
            User user= userService.findBySSO(ssoId);
            //only logs
            if (user!=null) LOG.info("authenticated user id:"+user.getId());
            else LOG.info("authenticated user is not yet in DB");
            //fix issue #9
            // if authenticated by SSO first time it is not in DB, thus create the user info in DB
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
