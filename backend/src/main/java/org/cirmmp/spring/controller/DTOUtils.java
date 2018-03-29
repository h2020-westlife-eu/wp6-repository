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
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

public class DTOUtils {
    //pass User information into DTO structure serialized by json.
    public static UserDTO getUserDTO(User user) {
        UserDTO udto= new UserDTO();
        udto.setFirstName(user.getFirstName());
        udto.setLastName(user.getLastName());
        udto.setEmail(user.getEmail());
        udto.setId(user.getId().toString());
        udto.setSsoId(user.getSsoId());

        if (user.getSsoId().contains("@")) { //if west-life user - set west-life link
            udto.setAccountLink("https://auth.west-life.eu/user");
            udto.setLogoutLink("/mellon/logout?ReturnTo=/");
        } else { //otherwise local links
            udto.setAccountLink("/admin/user");
            udto.setLogoutLink("/admin/logout");
        }
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
        dto.creation_date = dataSet.getCreation_date().toString();
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
        try {
            ds.setCreation_date(DateFormat.getDateInstance().parse(dto.creation_date));
        } catch (ParseException e){
            ds.setCreation_date(new Date());
        }
        ds.setDataInfo(dto.info);
        ds.setProject(projectService.findById(dto.projectId));
        ds.setSummary(dto.summary);
        //ds.setType(dto.);
        ds.setUri(dto.webdavurl);
        return ds;
    }

    public static ArrayList<DatasetDTO> getDatasetDTOS(List<DataSet> dataSets) {
        ArrayList<DatasetDTO> nfiles = new ArrayList<>();

        for (DataSet ds : dataSets) {
            DatasetDTO dto = new DatasetDTO();
            dto.name = ds.getDataName();
            dto.info = ds.getDataInfo();
            //infiles.setProjectId(ifile.getProjectId());
            dto.creation_date = ds.getCreation_date().toString();
            dto.summary = ds.getSummary();
            dto.webdavurl = ds.getUri();
            //if dataset has no project? it violates analysis dataset -> project, but we can set projectid=0
            dto.projectId = ds.getProject()!=null?ds.getProject().getId():0;
            dto.id = ds.getId();
            nfiles.add(dto);
        }
        return nfiles;
    }


    public static String ExecuteCommand(String cmdline) throws IOException {


        Process process = Runtime.getRuntime().exec(cmdline);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                process.getInputStream()));
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(
                process.getErrorStream()));
        String s;
        String output="";
        String error="";
        while ((s = reader.readLine()) != null) {
            output+=s;
        }
        while ((s = reader2.readLine()) != null) {
            error+=s;
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(output);
        }
        if (process.exitValue()!=0) throw new RuntimeException(error+"."+output);
        return output;
    }
}
