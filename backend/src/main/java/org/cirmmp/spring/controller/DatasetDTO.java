package org.cirmmp.spring.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class DatasetDTO {

    @Getter @Setter public String name;
    @Getter @Setter public String info;
    @Getter @Setter public Date creation_date;
    @Getter @Setter public String summary;
    @Getter @Setter public String webdavurl;
    @Getter @Setter public Long projectId;
    @Getter @Setter public Long id;

}
