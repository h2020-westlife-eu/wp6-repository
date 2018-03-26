package org.cirmmp.spring.controller;

import lombok.Getter;
import lombok.Setter;

/* DTO to encapsulate user meta information */
@Getter @Setter
public class UserDTO {
    private String FirstName;
    private String LastName;
    private String Email;
    private String Id;
    private String SsoId;
    private String AccountLink;
    private String LogoutLink;
}
