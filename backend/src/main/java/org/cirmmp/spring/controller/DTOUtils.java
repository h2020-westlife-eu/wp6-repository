package org.cirmmp.spring.controller;

import org.cirmmp.spring.model.Project;
import org.cirmmp.spring.model.User;

import java.security.SecureRandom;
import java.util.*;

public class DTOUtils {
    //pass User information into DTO structure serialized by json.
    public static UserDTO getUserDTO(User user) {
        UserDTO udto= new UserDTO();
        udto.FirstName = user.getFirstName();
        udto.LastName = user.getLastName();
        udto.Email = user.getEmail();
        udto.Id = user.getId().toString();
        udto.SsoId = user.getSsoId();
        return udto;
    }

    public static ProjectDTO getProjectDTO(Project project){
        ProjectDTO pdto = new ProjectDTO();
        pdto.creation_date=project.getCreation_date();
        pdto.hashId=project.getHashId();
        pdto.id=project.getId();
        pdto.projectName=project.getProjectName();
        pdto.shareable =project.getShearable();
        pdto.summary=project.getSummary();
        pdto.userId=project.getUserId();
        return pdto;
    }
    public static List<ProjectDTO> getProjectDTO(List<Project> projects){
        List<ProjectDTO> mylist = new ArrayList<ProjectDTO>();
        for (Project p: projects) mylist.add(getProjectDTO(p));
        return mylist;
    }
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


}
