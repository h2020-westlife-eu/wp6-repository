package org.cirmmp.spring.controller;

import com.google.gson.Gson;
import org.cirmmp.spring.model.DataSet;
import org.cirmmp.spring.service.DataSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/restcon")
public class DatasetServiceCon {
    private static final Logger LOG = LoggerFactory.getLogger(RestCon.class);
    private static Gson gson = new Gson();

    @Autowired
    DataSetService dataSetService;

    @RequestMapping(value = {"/dataset", "/dataset/{projectId}"}, method = RequestMethod.GET)
    public ResponseEntity listDataset(@PathVariable Optional<Long> projectId) {
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
        return new ResponseEntity(nfiles, HttpStatus.OK);
    }

    @RequestMapping(value = {"/dataset"}, method = RequestMethod.POST)
    public ResponseEntity createDataset(@PathVariable Optional<Long> projectId) {
        DataSet ds = new DataSet();
        ds.setCreation_date(new Date());
        ds.setDataName("");
        DatasetDTO dto = new DatasetDTO();
        return new ResponseEntity(dto,HttpStatus.OK);
    }
}