package org.cirmmp.spring.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/* DTO to encapsulate project meta info */
@Getter @Setter
public class ProjectDTO {
    Long id;
    Integer userId;
    String projectName;
    String summary;
    String shareable;
    String hashId;
    Date creation_date;
}
