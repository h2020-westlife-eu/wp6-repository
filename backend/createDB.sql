/*All User's gets stored in APP_USER table*/
DROP DATABASE IF EXISTS springw6;
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
DROP TABLE IF EXISTS APP_USER_USER_PROFILE;
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

DROP TABLE IF EXISTS PROJECT;
create table PROJECT (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT ,
  project_name VARCHAR(255) ,
  summary VARCHAR(2000) ,
  creation_date TIMESTAMP,
  hash_id VARCHAR(255),
  shareable VARCHAR(255),
  PRIMARY KEY (id)

);

DROP TABLE IF EXISTS DATASET;
create table DATASET (
  id BIGINT NOT NULL AUTO_INCREMENT,
  project_id BIGINT ,
  type VARCHAR(100),
  name VARCHAR(100),
  info VARCHAR(2000),
  summary VARCHAR(2000),
  uri VARCHAR(2000),
  creation_date TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT dataset_project FOREIGN KEY (project_id) REFERENCES PROJECT (id) ON UPDATE CASCADE ON DELETE CASCADE
);

/* Set max size for upload files on mysql database*/
SET GLOBAL max_allowed_packet = 33554432 ;