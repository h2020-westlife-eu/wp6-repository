/*All User's gets stored in APP_USER table*/
DROP DATABASE springw6;
CREATE DATABASE springw6;
use springw6;
DROP TABLE IF EXISTS APP_USER;
create table APP_USER (
   id BIGINT NOT NULL AUTO_INCREMENT,
   sso_id VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   first_name VARCHAR(255) NOT NULL,
   last_name  VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (sso_id)
);

/* USER_PROFILE table contains all possible roles */
DROP TABLE IF EXISTS USER_PROFILE;
create table USER_PROFILE(
   id BIGINT NOT NULL AUTO_INCREMENT,
   type VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (type)
);

/* JOIN TABLE for MANY-TO-MANY relationship*/
/* DROP TABLE IF EXISTS 'USER_PROFILE';*/
CREATE TABLE APP_USER_USER_PROFILE (
    user_id BIGINT NOT NULL,
    user_profile_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, user_profile_id),
    CONSTRAINT FK_APP_USER FOREIGN KEY (user_id) REFERENCES APP_USER (id),
    CONSTRAINT FK_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES USER_PROFILE (id)
);

/* Create persistent_logins Table used to store rememberme related stuff*/
DROP TABLE IF EXISTS persistent_logins;
CREATE TABLE PERSISTENT_LOGINS (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
);

DROP TABLE IF EXISTS project;
create table PROJECT (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT ,
  project_name VARCHAR(255) ,
  summary VARCHAR(2000) ,
  PRIMARY KEY (id),
  KEY FK_USER (id),
  CONSTRAINT FK_USER FOREIGN KEY (id) REFERENCES APP_USER (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS filelist;
create table FILELIST (
  id BIGINT NOT NULL AUTO_INCREMENT,
  project_id BIGINT ,
  file_name VARCHAR(100) ,
  file_info VARCHAR(2000) ,
  PRIMARY KEY (id),
  KEY FK_PROJECT (project_id),
  CONSTRAINT FK_PROJECT FOREIGN KEY (project_id) REFERENCES PROJECT (id) ON DELETE CASCADE ON UPDATE CASCADE

);

/* Populate USER_PROFILE Table */
INSERT INTO USER_PROFILE(type)
VALUES ('USER');

INSERT INTO USER_PROFILE(type)
VALUES ('ADMIN');

INSERT INTO USER_PROFILE(type)
VALUES ('DBA');

/* Populate one Admin User which will further create other users for the application using GUI */
/* password: user   */
INSERT INTO APP_USER(id,sso_id, password, first_name, last_name, email)
VALUES (1,'user','$2a$10$q1M2rwLvNFOXiArJAG7OFei49Aj1WJrF6CDveoAOEixUAk5e5uNWW', 'User','User','user@xyz.com'),
(2,'30d14bd81ab385bdccb3286406131f39021ba308@west-life.eu','$2a$10$q1M2rwLvNFOXiArJAG7OFei49Aj1WJrF6CDveoAOEixUAk5e5uNWW', 'Tomas','Kulhanek','tomas.kulhanek@stfc.ac.uk');

/* Populate JOIN Table */
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT user.id, profile.id FROM APP_USER user, USER_PROFILE profile
  where user.sso_id='user' and profile.type='ADMIN';

/* create demo project */
INSERT INTO PROJECT(user_id,project_name,summary)
    VALUES (2,"Strychnin NMR analysis","This project analyses strychnine and binding sites of glycine receptors");
