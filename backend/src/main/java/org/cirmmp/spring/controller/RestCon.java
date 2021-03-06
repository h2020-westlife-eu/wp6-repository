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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    DataSetService dataSetService;

    @Autowired
    UserProfileService userProfileService;

    @RequestMapping(value = { "/authsso" },method = RequestMethod.POST)
    public ResponseEntity authSSO(final HttpServletRequest request,@RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname, @RequestHeader(name="X-EMAIL",defaultValue="") String xemail, @RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        //if not custom headers are set - then return currently authenticated user by spring.
        if (xusername.length()==0) return new ResponseEntity (gson.toJson(DTOUtils.getUserDTO(user)), HttpStatus.OK);
        AutoUser autoUser =new AutoUser();
  //      String[] names = xname.split(" ");
        autoUser.setFirstName(user.getFirstName());
        autoUser.setLastName(user.getLastName());
        autoUser.setEmail(user.getEmail());
        autoUser.setPassword(user.getPassword());
        //chosose from USER ADMIN DBA all role have to be defined defined on SecurityConfiguration
        autoUser.setRole("ADMIN");
        Authentication auth = new UsernamePasswordAuthenticationToken(autoUser,
                autoUser.getPassword(), autoUser.getAuthorities());


        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);

        //SecurityContextHolder.getContext().setAuthentication(auth);
//        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        LOG.info("logged in user:"+u.getPrincipal().toString());
        if (user!=null) return new ResponseEntity (gson.toJson(DTOUtils.getUserDTO(user)), HttpStatus.OK);
        else return new ResponseEntity("user not authenticated",HttpStatus.UNAUTHORIZED);
    }

    /* returns spring authenticated as well as SSO authenticated (in http headers)*/
    @Secured("USER")
    @RequestMapping(value = { "/user" },method = RequestMethod.GET)
    public ResponseEntity listOrder(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication2(xusername,xname,xemail,xgroups);
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




}
