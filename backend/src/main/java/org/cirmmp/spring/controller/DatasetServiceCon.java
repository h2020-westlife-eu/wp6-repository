package org.cirmmp.spring.controller;

import com.google.gson.Gson;
import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.Project;
import org.cirmmp.spring.service.DataSetService;
import org.cirmmp.spring.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/restcon")
public class DatasetServiceCon {
    private static final Logger LOG = LoggerFactory.getLogger(RestCon.class);
    //REP_USER_DIR environment variable sets directory where new dataset may resist
    private static final String USER_DIR=Optional.ofNullable(System.getenv("REP_USER_DIR")).orElse("/home/vagrant/work/");
    private static final String SCRIPT_DIR=Optional.ofNullable(System.getenv("REP_SCRIPT_DIR")).orElse("/home/vagrant/wp6-repository/scripts/");
    private static Gson gson = new Gson();

    @Autowired
    DataSetService dataSetService;

    public static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");

    @RequestMapping(value = {"/dataset", "/dataset/{projectId}"}, method = RequestMethod.GET)
    public ResponseEntity listDataset(@PathVariable Optional<Long> projectId) {
        LOG.info("listing datasets, projectId is set:"+projectId.isPresent());
        List<DataSet> files;
        if (projectId.isPresent()) {
            //TODO implement it in DB layer dataSetService.findByProjectId(projectId.get())
            files = dataSetService.findAllDataset();
            for (Iterator<DataSet> iter = files.iterator(); iter.hasNext(); ) {
                DataSet a = iter.next();
                //remove datasets that belongs to other project, or that doesn't belong to project
                if ((a.getProject()==null) || (a.getProject().getId() != projectId.get())) {
                    iter.remove();
                }
            }

        } else {
            files = dataSetService.findAllDataset();
        }

        ArrayList<DatasetDTO> nfiles = new ArrayList<>();

        for (DataSet ds : files) {
            DatasetDTO dto = new DatasetDTO();
            dto.name = ds.getDataName();
            dto.info = ds.getDataInfo();
            //infiles.setProjectId(ifile.getProjectId());
            dto.creation_date = ds.getCreation_date();
            dto.summary = ds.getSummary();
            dto.webdavurl = ds.getUri();
            //if dataset has no project? it violates analysis dataset -> project, but we can set projectid=0
            dto.projectId = ds.getProject()!=null?ds.getProject().getId():0;
            dto.id = ds.getId();
            nfiles.add(dto);
        }
        LOG.info("listing datasets,nfiles:"+files.size());
/*
        CsrfToken token = (CsrfToken) request.getSession().getAttribute(DEFAULT_CSRF_TOKEN_ATTR_NAME);
        //now setting the csrf token as http header, client can use it in subsequent POST call
        if (token==null) token = (CsrfToken) request.getSession().getAttribute("_csrf");
        if (token==null) token = (CsrfToken) request.getAttribute("_csrf");
// Spring Security will allow the Token to be included in this header name
        //response.setHeader("X-CSRF-HEADER", token.getHeaderName());
// Spring Security will allow the token to be included in this parameter name
        //response.setHeader("X-CSRF-PARAM", token.getParameterName());
// this is the value of the token to be included as either a header or an HTTP parameter
        response.setHeader("X-CSRF-TOKEN", token.getToken());
*/
        return new ResponseEntity(nfiles, HttpStatus.OK);
    }

    @Autowired
    ProjectService projectService;



    @RequestMapping(value = {"/dataset"}, method = POST )
    public @ResponseBody ResponseEntity addDataset(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups,@RequestBody DatasetDTO dto){
        LOG.info("addDataset()");
        if (dto.projectId!=null) {
            //create webdavurl
            String proxycontext= DTOUtils.randomString(8);
            String userdir = USER_DIR+xusername.hashCode()+"/"+proxycontext;
            //TODO check whether webdavurl is unique
            try {
                String output=DTOUtils.ExecuteCommand("sudo "+SCRIPT_DIR+"controlproxy.sh -a " + userdir+" "+proxycontext);
                LOG.debug("controlproxy.sh:\n"+output);
                dto.setCreation_date(new Date());
                //proxy is created in /files/[proxycontext]
                dto.setWebdavurl(getUriFromContext(proxycontext));

                // creating dataset in DB
                Project project = projectService.findById(dto.projectId);
                DataSet ds = DTOUtils.getDataset(dto, projectService);
                dataSetService.save(ds);
                dto.id = ds.getId();
                return new ResponseEntity(dto, HttpStatus.OK);
            } catch (Exception e){
                LOG.error(e.getMessage(),e);
                return new ResponseEntity("{\"error\":\""+e.getMessage()+"\"}",HttpStatus.INTERNAL_SERVER_ERROR);

            }
        } else
            return new ResponseEntity("{\"error\":\"projectId needs to be set\"}",HttpStatus.NOT_FOUND);
    }

    private String getUriFromContext(String proxycontext) {
        return "/files/"+proxycontext+"/";
    }
    private String getContextFromUri(String uri){
        // uri = "/files/X54uf094rsfi34/" -> context="X54uf094rsfi34"
        // some datasets may have null uri - return null for context
        String context = uri!=null?uri.substring(uri.indexOf('/',1)+1,uri.lastIndexOf('/')):null;
        LOG.debug(context);
        return context;

    }

    //TODO may check authorization - some table with relationship user:dataset
    @RequestMapping(value = {"/dataset/{id}"}, method = DELETE )
    public @ResponseBody ResponseEntity deleteDataset(@PathVariable Long id,@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups){
        LOG.info("deleteDataset()");
        try {
            //delete from filesystem - checks whether user has such dataset mapped, if not dataset won't be deleted
            DataSet ds = dataSetService.findById(id);
            String proxycontext = getContextFromUri(ds.getUri());
            String userdir = USER_DIR+xusername.hashCode()+"/"+proxycontext;
            String output =DTOUtils.ExecuteCommand("sudo "+SCRIPT_DIR+"controlproxy.sh -r " + userdir+" "+proxycontext);
            LOG.debug("controlproxy.sh:\n"+output);
            //delete from DB
            dataSetService.deleteById(id);
            return new ResponseEntity(id, HttpStatus.OK);
        } catch (Exception e){
            LOG.error(e.getMessage(),e);
            return new ResponseEntity("{\"error\":\""+e.getMessage()+"\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}