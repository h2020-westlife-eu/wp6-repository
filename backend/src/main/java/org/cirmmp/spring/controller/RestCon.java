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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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
public class RestCon extends SharedCon{
    private static final Logger LOG = LoggerFactory.getLogger(RestCon.class);
    private static Gson gson = new Gson();

    @Autowired
    UserService userService;

    @Autowired
    WebDAVCopyUtils webDAVCopyUtils;


    @Autowired
    ProjectService projectService;

    @Autowired
    FileListService fileListService;

    @Autowired
    DataSetService dataSetService;

    @Autowired
    UserProfileService userProfileService;

    @RequestMapping(value = { "/authsso" },method = RequestMethod.POST)
    public ResponseEntity authSSO(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname, @RequestHeader(name="X-EMAIL",defaultValue="") String xemail, @RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        AutoUser autoUser =new AutoUser();
        String[] names = xname.split(" ");
        autoUser.setFirstName(names[1]);
        autoUser.setLastName(names[names.length-1]);
        autoUser.setEmail(xemail);
        autoUser.setPassword(DTOUtils.randomString(30));
        //chosose from USER ADMIN DBA all role have to be defined defined on SecurityConfiguration
        autoUser.setRole("USER");
        Authentication auth = new UsernamePasswordAuthenticationToken(autoUser,
                autoUser.getPassword(), autoUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        LOG.info("logged in user:"+SecurityContextHolder.getContext().getAuthentication().getName());
        if (user!=null) return new ResponseEntity (gson.toJson(DTOUtils.getUserDTO(user)), HttpStatus.OK);
        else return new ResponseEntity("user not authenticated",HttpStatus.UNAUTHORIZED);
    }

    /* returns spring authenticated as well as SSO authenticated (in http headers)*/
    @RequestMapping(value = { "/user" },method = RequestMethod.GET)
    public ResponseEntity listOrder(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        if (user!=null) return new ResponseEntity (gson.toJson(DTOUtils.getUserDTO(user)), HttpStatus.OK);
        else return new ResponseEntity("user not authenticated",HttpStatus.UNAUTHORIZED);
    }


    /* return list of users registered inside repository DB*/
    @Secured("USER")
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
    //public ResponseEntity listProject(HttpServletRequest request, @RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname, @RequestHeader(name="X-EMAIL",defaultValue="") String xemail, @RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
    public ResponseEntity listProject(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname, @RequestHeader(name="X-EMAIL",defaultValue="") String xemail, @RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        //User user = checkAuthentication(request,xusername,xname,xemail,xgroups);
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        LOG.info("listProject()");
        //String ssoId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Project> projects =projectService.findByUserId(user.getId());
        LOG.info("list projects size:"+projects.size()+" first: "+ ((projects.size()>0) ? (projects.get(0).getProjectName()) : "NA"));
        return new ResponseEntity(gson.toJson(DTOUtils.getProjectDTO(projects)), HttpStatus.OK);
    }

    // check using method security interceptor if the user have role USER
    @Secured("USER")
    @RequestMapping(value = "/projectlist", method=RequestMethod.GET)
    //public ResponseEntity listProject(HttpServletRequest request, @RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname, @RequestHeader(name="X-EMAIL",defaultValue="") String xemail, @RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
    public ResponseEntity listProjectAll(){
        //User user = checkAuthentication(request,xusername,xname,xemail,xgroups);
        User user = checkAuthentication("","","","");
        //String ssoId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Project> projects =projectService.findAllProject();
        LOG.info("list projects size:"+projects.size()+" first: "+ ((projects.size()>0) ? (projects.get(0).getProjectName()) : "NA"));
        return new ResponseEntity(gson.toJson(DTOUtils.getProjectDTO(projects)), HttpStatus.OK);
    }

//Dataset moved to DatasetServiceCon
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


}
