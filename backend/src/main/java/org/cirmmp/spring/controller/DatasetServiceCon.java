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

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/restcon")
public class DatasetServiceCon {
    private static final Logger LOG = LoggerFactory.getLogger(RestCon.class);
    private static Gson gson = new Gson();

    @Autowired
    DataSetService dataSetService;

    public static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");

    @RequestMapping(value = {"/dataset", "/dataset/{projectId}"}, method = RequestMethod.GET)
    public ResponseEntity listDataset(@PathVariable Optional<Long> projectId, HttpServletRequest request, HttpServletResponse response) {
        LOG.info("listing datasets, projectId is set:"+projectId.isPresent());
        List<DataSet> files;
        if (projectId.isPresent()) {
            //TODO implement it in DB layer dataSetService.findByProjectId(projectId.get())
            files = dataSetService.findAllDataset();
            for (Iterator<DataSet> iter = files.iterator(); iter.hasNext(); ) {
                DataSet a = iter.next();
                if (a.getProject().getId() != projectId.get()) {
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
            dto.projectId = ds.getProject().getId();
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
    public @ResponseBody ResponseEntity addDataset(@RequestBody DatasetDTO dto){
        LOG.info("addDataset()");
        Project project = projectService.findById(dto.projectId);
        DataSet ds = DTOUtils.getDataset(dto, projectService);
        dataSetService.save(DTOUtils.getDataset(dto,projectService));
        // datasetService should return dataSet of newly created persistent resource - getId()
        dto.id=ds.getId();
        return new ResponseEntity(dto,HttpStatus.OK);
    }

    @RequestMapping(value = "/dataset2", method=POST)
    public @ResponseBody ResponseEntity addDataset(){
        LOG.info("addDataset()");
        /*Project project = projectService.findById(dto.projectId);
        DataSet ds = DTOUtils.getDataset(dto, projectService);
        dataSetService.save(DTOUtils.getDataset(dto,projectService));
        // datasetService should return dataSet of newly created persistent resource - getId()
        dto.id=ds.getId();*/
        DatasetDTO dto = new DatasetDTO();
        dto.name="empty dataset";
        return new ResponseEntity(dto,HttpStatus.OK);
    }

}