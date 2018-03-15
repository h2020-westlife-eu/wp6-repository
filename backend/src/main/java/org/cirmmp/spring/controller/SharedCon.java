package org.cirmmp.spring.controller;

import com.google.gson.Gson;
import org.cirmmp.spring.model.AutoUser;
import org.cirmmp.spring.model.User;
import org.cirmmp.spring.model.UserProfile;
import org.cirmmp.spring.service.UserProfileService;
import org.cirmmp.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;

/**
 * Shared methods
 */

public class SharedCon {
    /** utils to create user in database if it is logged using SSO and not in DB yet
     *
     */
    private static final Logger LOG = LoggerFactory.getLogger(RestCon.class);

    @Autowired
    UserService userService;
    @Autowired
    UserProfileService userProfileService;

    private static Gson gson = new Gson();


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

        //TODO authenticate user also in other requests, not only in case it doesn't exist in DB??
        //AUTO Authententicate USER
        // Authenticate the user on spring framework after creation
        AutoUser autoUser =new AutoUser();
        autoUser.setFirstName(getFirstNames(names));
        autoUser.setLastName(names[names.length-1]);
        autoUser.setEmail(xemail);
        autoUser.setPassword(DTOUtils.randomString(30));
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
    protected synchronized User checkAuthentication(String xusername,String xname,String xemail,String xgroups){
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
                    return null;//HttpStatus.UNAUTHORIZED
                    //throw new AuthenticationCredentialsNotFoundException("authorization required");
                }
            }
            LOG.info("checkAuthentication: OK");
            return user;
        }
    }
    //checks authentication, either it is authenticated via spring - or via sent arguments x*
    //synchronized - multiple calls might be concurrent in - userService.findBySSO(ssoid);
    protected synchronized User checkAuthentication2(String xusername,String xname,String xemail,String xgroups){
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
            else throw new AuthenticationCredentialsNotFoundException("authenticated user is not in DB");
            LOG.info("checkAuthentication: OK");
            return user;
        }
    }

}
