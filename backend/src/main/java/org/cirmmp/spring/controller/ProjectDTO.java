package org.cirmmp.spring.controller;

import java.util.Date;

/* DTO to encapsulate project meta info */
public class ProjectDTO {
    Long id;
    Integer userId;
    String projectName;
    String summary;
    String shearable;
    String hashId;
    Date creation_date;
}
