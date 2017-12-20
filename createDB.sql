/*All User's gets stored in APP_USER table*/

DROP TABLE IF EXISTS APP_USER;
create table APP_USER (
   id BIGINT NOT NULL AUTO_INCREMENT,
   sso_id VARCHAR(30) NOT NULL,
   password VARCHAR(100) NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   email VARCHAR(30) NOT NULL,
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
DROP TABLE IF EXISTS 'USER_PROFILE';
CREATE TABLE APP_USER_USER_PROFILE (
    user_id BIGINT NOT NULL,
    user_profile_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, user_profile_id),
    CONSTRAINT FK_APP_USER FOREIGN KEY (user_id) REFERENCES APP_USER (id),
    CONSTRAINT FK_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES USER_PROFILE (id)
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
INSERT INTO APP_USER(sso_id, password, first_name, last_name, email)
VALUES ('user','$2a$10$q1M2rwLvNFOXiArJAG7OFei49Aj1WJrF6CDveoAOEixUAk5e5uNWW', 'User','User','user@xyz.com');


/* Populate JOIN Table */
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT user.id, profile.id FROM app_user user, user_profile profile
  where user.sso_id='user' and profile.type='ADMIN';

/* Create persistent_logins Table used to store rememberme related stuff*/
DROP TABLE IF EXISTS persistent_logins;
CREATE TABLE persistent_logins (
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
  project_name VARCHAR(30) ,
  summary VARCHAR(2000) ,
  creation_date TIMESTAMP,
  PRIMARY KEY (id)

);

DROP TABLE IF EXISTS filelist;
create table FILELIST (
  id BIGINT NOT NULL AUTO_INCREMENT,
  project_id BIGINT ,
  type VARCHAR(100),
  file_name VARCHAR(100) ,
  file_info VARCHAR(2000) ,
  content LONGBLOB,
  PRIMARY KEY (id),
  CONSTRAINT filelist_project FOREIGN KEY (project_id) REFERENCES PROJECT (id) ON UPDATE CASCADE ON DELETE CASCADE
);

/* Set max size for upload files on mysql database*/
SET GLOBAL max_allowed_packet = 33554432 ;