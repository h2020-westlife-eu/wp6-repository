package org.cirmmp.spring.controller;

import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.Project;
import org.cirmmp.spring.model.User;
import org.cirmmp.spring.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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


    public static DatasetDTO getDatasetDTO(DataSet dataSet,Long pid) {
        DatasetDTO dto = new DatasetDTO();
        dto.creation_date = dataSet.getCreation_date();
        dto.id=dataSet.getId();
        dto.info=dataSet.getDataInfo();
        dto.name=dataSet.getDataName();
        dto.summary=dataSet.getSummary();
        dto.projectId=pid;
        dto.webdavurl=dataSet.getUri();
        return dto;
    }


    public static DataSet getDataset(DatasetDTO dto,ProjectService projectService) {
        DataSet ds = new DataSet();
        ds.setId(dto.id);
        ds.setDataName(dto.name);
        ds.setCreation_date(dto.creation_date);
        ds.setDataInfo(dto.info);
        ds.setProject(projectService.findById(dto.projectId));
        ds.setSummary(dto.summary);
        //ds.setType(dto.);
        ds.setUri(dto.webdavurl);
        return ds;
    }

    public static String ExecuteCommand(String cmdline) throws IOException {

        Process process = Runtime.getRuntime().exec(cmdline);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                process.getInputStream()));
        String s;
        String output="";
        while ((s = reader.readLine()) != null) {
            output+=s;
        }
        return output;
    }
}
