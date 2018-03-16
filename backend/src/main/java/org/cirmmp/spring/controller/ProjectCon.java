package org.cirmmp.spring.controller;

import com.google.gson.Gson;
import org.cirmmp.spring.model.FileList;
import org.cirmmp.spring.model.Project;
import org.cirmmp.spring.model.User;
import org.cirmmp.spring.model.json.JFileList;
import org.cirmmp.spring.model.json.JProject;
import org.cirmmp.spring.service.FileListService;
import org.cirmmp.spring.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/restcon")
public class ProjectCon extends SharedCon{
    private static final Logger LOG = LoggerFactory.getLogger(RestCon.class);
    private static Gson gson = new Gson();
    @Autowired
    FileListService fileListService;
    @Autowired
    ProjectService projectService;
    //'/createproject' and '/listproject' urls violates common HATEOAS of REST - it should be all '/project' - verb is already specified in http request
    // POST = create, PUT=update, GET = list GET/id = project detail, DELETE = delete, application/json - spring doesn't operate it automatically?
    // Does spring framework have some automatic handling of content type - application/json is processed by json, application/xml is proccessed by some xml binding
    // tool?
    @Secured("USER")
    @RequestMapping(value = {"/project"}, method= RequestMethod.POST,consumes = {"application/json"},produces = {"application/json"})
    public ResponseEntity createProject(@RequestBody JProject jProject, @RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname, @RequestHeader(name="X-EMAIL",defaultValue="") String xemail, @RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        User user = checkAuthentication2(xusername,xname,xemail,xgroups);
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
        User user = checkAuthentication2(xusername,xname,xemail,xgroups);
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
        User user = checkAuthentication2("","","","");
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
