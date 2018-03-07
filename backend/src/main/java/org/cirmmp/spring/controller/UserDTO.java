package org.cirmmp.spring.controller;

import lombok.Getter;
import lombok.Setter;

/* DTO to encapsulate user meta information */
@Getter @Setter
public class UserDTO {
    String FirstName;
    String LastName;
    String Email;
    String Id;
    String SsoId;
}
