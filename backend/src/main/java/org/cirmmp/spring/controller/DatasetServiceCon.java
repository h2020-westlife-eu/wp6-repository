package org.cirmmp.spring.controller;

import com.google.gson.Gson;
import lombok.Synchronized;
import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.model.Project;
import org.cirmmp.spring.model.User;
import org.cirmmp.spring.service.DataSetService;
import org.cirmmp.spring.metadata.MetadataGenerator;
import org.cirmmp.spring.service.ProjectService;
import org.cirmmp.spring.service.WebDAVCopyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/restcon")
public class DatasetServiceCon extends SharedCon {
    private static final Logger LOG = LoggerFactory.getLogger(RestCon.class);
    //REP_USER_DIR environment variable sets directory where new dataset may resist
    private static final String REP_LOCATION=Optional.ofNullable(System.getenv("REP_LOCATION")).orElse("/home/vagrant/wp6-repository");
    private static final String USER_DIR=Optional.ofNullable(System.getenv("VF_STORAGE_DIR")).orElse("/home/vagrant/work");
    private static final String TEST_DIR=REP_LOCATION+"/test/";
    private static final String SCRIPT_DIR=REP_LOCATION+"/scripts/";
    private static Gson gson = new Gson();

    @Autowired
    DataSetService dataSetService;

    @Autowired
    WebDAVCopyUtils webDAVCopyUtils;

    public static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");

    public ResponseEntity listDataset(@PathVariable Optional<Long> projectId) {
        return listDataset(projectId,"","","","");
    }

    //TODO remove???
    @RequestMapping(value = {"/datasets", "/project/{projectId}/datasets"}, method = GET)
    public ResponseEntity listDatasets(@PathVariable Optional<Long> projectId) {
        List<DataSet> dataSets;

        if (projectId.isPresent()) {
            dataSets = dataSetService.findAllDataset();
            for (Iterator<DataSet> iter = dataSets.iterator(); iter.hasNext(); ) {
                DataSet a = iter.next();
                //remove datasets that belongs to other project, or that doesn't belong to project
                if ((a.getProject() == null) || (a.getProject().getId() != projectId.get())) {
                    iter.remove();
                }
            }
        } else {
            dataSets = dataSetService.findAllDataset();

        }

        return new ResponseEntity(DTOUtils.getDatasetDTOS(dataSets), HttpStatus.OK);
    }

    @Secured("ADMIN")
    @RequestMapping(value = {"/user/{userId}/dataset"}, method = GET)
    public ResponseEntity listUserDataset(@PathVariable int userId) {
        List<DataSet> dataSets;
        dataSets = dataSetService.findAllDataset();

        //TODO it could be done by SQL query instead of iterating over results here.
        // filter by project owned by user
        // get projectids belonging to projects owned by user
        List<Long> projectids =projectService.findByUserId(userId).stream().map(p -> p.getId()).collect(Collectors.toList());
        //remove datasets that doesn't belong to project among projectids
        for (Iterator<DataSet> iter = dataSets.iterator();iter.hasNext();){
            DataSet a = iter.next();
            //dataset without project will not be returned, dataset with project belonging to somebody else will be removed
            if ((a.getProject()==null) || (!(projectids.contains(a.getProject().getId())))) {
                iter.remove();
            }
        }
        return new ResponseEntity(DTOUtils.getDatasetDTOS(dataSets), HttpStatus.OK);
    }

    //@Secured seems not to be taken into account
    @Secured("USER")
    @RequestMapping(value = {"/dataset", "/project/{projectId}/dataset"}, method = GET)
    public ResponseEntity listDataset(@PathVariable Optional<Long> projectId,@RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname, @RequestHeader(name="X-EMAIL",defaultValue="") String xemail, @RequestHeader(name="X-GROUPS",defaultValue="") String xgroups) {
        //User user = checkAuthentication(request,xusername,xname,xemail,xgroups);
        User user = checkAuthentication(xusername,xname,xemail,xgroups);


        LOG.info("listing datasets, projectId is set:"+projectId.isPresent());
        List<DataSet> dataSets;

        if (projectId.isPresent()) {
            //TODO implement it in DB layer dataSetService.findByProjectId(projectId.get())
            dataSets = dataSetService.findAllDataset();

            for (Iterator<DataSet> iter = dataSets.iterator(); iter.hasNext(); ) {
                DataSet a = iter.next();
                //remove datasets that belongs to other project, or that doesn't belong to project
                if ((a.getProject()==null) || (a.getProject().getId() != projectId.get())) {
                    //TODO is it OK to remove object from iterator directly?
                    iter.remove();
                }
            }

        } else {
            dataSets = dataSetService.findAllDataset();

            //TODO it could be done by SQL query instead of iterating over results here.
            // filter by project owned by user
            // get projectids belonging to projects owned by user
            List<Long> projectids =projectService.findByUserId(user.getId()).stream().map(p -> p.getId()).collect(Collectors.toList());
            //remove datasets that doesn't belong to project among projectids
            for (Iterator<DataSet> iter = dataSets.iterator();iter.hasNext();){
                DataSet a = iter.next();
                //dataset without project will not be returned, dataset with project belonging to somebody else will be removed
                if ((a.getProject()==null) || (!(projectids.contains(a.getProject().getId())))) {
                    iter.remove();
                }
            }
        }


        return new ResponseEntity(DTOUtils.getDatasetDTOS(dataSets), HttpStatus.OK);
    }
    //@Secured seems not to be taken into account, Put it to RestCon.java
    @Secured("USER")
    @RequestMapping(value = {"/dataset/{datasetId}"}, method = GET)
    public ResponseEntity getDataset(@PathVariable Long datasetId,@RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname, @RequestHeader(name="X-EMAIL",defaultValue="") String xemail, @RequestHeader(name="X-GROUPS",defaultValue="") String xgroups) {
        //User user = checkAuthentication(request,xusername,xname,xemail,xgroups);
        User user = checkAuthentication(xusername,xname,xemail,xgroups);


        LOG.info("listing dataset "+datasetId);
        DataSet dataset = dataSetService.findById(datasetId);
//TODO check dataset belongs to user, otherwise return HTTP 403
        return new ResponseEntity(DTOUtils.getDatasetDTO(dataset), HttpStatus.OK);
    }

    //@Secured seems not to be taken into account, Put it to RestCon.java
    @Secured("USER")
    @RequestMapping(value = {"/dataset/{datasetId}/metadata"}, method = GET)
    public ResponseEntity getDatasetMetadata(@PathVariable Long datasetId,@RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname, @RequestHeader(name="X-EMAIL",defaultValue="") String xemail, @RequestHeader(name="X-GROUPS",defaultValue="") String xgroups) {
        //User user = checkAuthentication(request,xusername,xname,xemail,xgroups);
        User user = checkAuthentication(xusername,xname,xemail,xgroups);


        LOG.info("listing dataset "+datasetId);
        DataSet dataset = dataSetService.findById(datasetId);
//TODO check dataset belongs to user, otherwise return HTTP 403
        return new ResponseEntity(dataset.getMetadata(), HttpStatus.OK);
    }

//this POST call initiate to set metadata,
// if nothing is in BODY, then harvest metadata from files included in the dataset
// otherwise the BODY of the request is set as metadata of the dataset (String) - it is expected to be some JSON format

    @Secured("USER")
    @RequestMapping(value = {"/dataset/{datasetId}/metadata"}, method = POST)
    public ResponseEntity postDataset(@PathVariable Long datasetId,@RequestHeader(name="X-USERNAME",defaultValue="") String xusername, @RequestHeader(name="X-NAME",defaultValue="") String xname, @RequestHeader(name="X-EMAIL",defaultValue="") String xemail, @RequestHeader(name="X-GROUPS",defaultValue="") String xgroups,@RequestBody(required = false) String datasetMetadata) {
        //User user = checkAuthentication(request,xusername,xname,xemail,xgroups);
        User user = checkAuthentication(xusername,xname,xemail,xgroups);
        LOG.info("listing dataset "+datasetId);
        DataSet dataset = dataSetService.findById(datasetId);
        LOG.debug("debug metadata:"+datasetMetadata+"\n");
        if (datasetMetadata== null || datasetMetadata.isEmpty()) {
            String metadata = "{}";
            //now generate metadata - harvest it from files
            metadata = MetadataGenerator.harvestMetadata(dataset,getUserdir(xusername, getContextFromUri(dataset.getUri())));
            dataset.setMetadata(metadata);
            dataSetService.saveExisting(dataset);
        }
        else {
          dataset.setMetadata(datasetMetadata);
          dataSetService.saveExisting(dataset);
        }
//TODO check dataset belongs to user, otherwise return HTTP 403
        return new ResponseEntity(dataset.getMetadata(), HttpStatus.OK);
    }


    @Autowired
    ProjectService projectService;


    private Object lock1 = new Object();

    @Secured("USER")
    @RequestMapping(value = {"/dataset"}, method = POST )
    public @ResponseBody ResponseEntity addDataset(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups,@RequestBody DatasetDTO dto){
        LOG.info("addDataset()");
        if (dto.projectId!=null) {
            //create webdavurl
            String proxycontext= DTOUtils.randomString(8);
            Project project = projectService.findById(dto.projectId);
            //if this is done by STAFF - TODO need to check authorization - secured(user) should be enough - but needs to work
            if (xusername.length()==0) xusername = userService.findById(project.getUserId()).getSsoId();
            String userdir = getUserdir(xusername, proxycontext);
            //TODO check whether webdavurl is unique
            try {
                String output=DTOUtils.ExecuteCommand("sudo "+SCRIPT_DIR+"controlproxy.sh -a " + userdir+" "+proxycontext);
                LOG.debug("controlproxy.sh:\n"+output);
                dto.setCreation_date(new Date().toString());
                //proxy is created in /files/[proxycontext]
                dto.setWebdavurl(getUriFromContext(proxycontext));

                // creating dataset in DB
                //Project project = projectService.findById(dto.projectId);
                DataSet ds = DTOUtils.getDataset(dto, projectService);

                synchronized (lock1) {
                    dataSetService.save(ds);
                    dto.id = ds.getId();
                }
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
            String userdir = getUserdir(xusername, proxycontext);
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

    private String getUserdir(String xusername, String proxycontext) {
        String uh = "U";
        if (xusername.length()>0) uh = getHash(xusername);
        return USER_DIR+"/"+uh+"/"+proxycontext;
    }

    private String getHash(String xusername) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(xusername.getBytes());
            String hash= new String (Base64.getEncoder().encode(md.digest()));
            hash = hash.replaceAll("[+//=]","-");
            return hash;
        } catch (NoSuchAlgorithmException e){
            return "U"+xusername.hashCode();
        }
    }

    private String getTestdir(String proxycontext) {
        return TEST_DIR+proxycontext;
    }

    /**
     * initiate copy action on dataset
     * @param dto - dto containing dataset uri and webdav destination url
     * @return status
     */
    static ArrayList<CopyTaskDTO> copytasks = new ArrayList<>();


    @RequestMapping(value = {"/copytask"}, method = POST )
    @Synchronized
    public @ResponseBody ResponseEntity copyDataset(@RequestHeader(name="X-USERNAME",defaultValue="") String xusername,@RequestHeader(name="X-NAME",defaultValue="") String xname,@RequestHeader(name="X-EMAIL",defaultValue="") String xemail,@RequestHeader(name="X-GROUPS",defaultValue="") String xgroups,@RequestBody CopyTaskDTO dto){
        if (dto.getWebdavurl().length()>0) {
                int id = copytasks.lastIndexOf(dto);
                if (id > -1 && !copytasks.get(id).getDone())
                    return new ResponseEntity(id, HttpStatus.OK); //second request to copy same returns already existing ID
                String dsdir = getUserdir(xusername, getContextFromUri(dto.getSourceurl()));
                if (!Files.exists(Paths.get(dsdir))) dsdir = getTestdir(getContextFromUri(dto.getSourceurl()));
                //TODO consider to return task id, which may then contain progress, status, start/end time

            if (Files.exists(Paths.get(dsdir))) {
                copytasks.add(dto);
                id = copytasks.lastIndexOf(dto);
                webDAVCopyUtils.asyncCopyTask(copytasks,id,dsdir,dto.getWebdavurl());
                return new ResponseEntity(id, HttpStatus.OK);
            }
            else
                return new ResponseEntity("{\"error\":\"source url not corresponding to any local resources\"}",HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity("{\"error\":\"specify webdavurl\"}",HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = {"/copytask"}, method = GET )
    public @ResponseBody ResponseEntity getTasks(){
        return new ResponseEntity(gson.toJson(copytasks),HttpStatus.OK);
    }

    @RequestMapping(value = {"/copytask/{id}"}, method = GET )
    public @ResponseBody ResponseEntity getTasksbyId(@PathVariable int id){
        return new ResponseEntity(gson.toJson(copytasks.get(id)),HttpStatus.OK);
    }

}