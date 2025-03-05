package com.totalMiniBmw.tmb_server.dto;

import com.totalMiniBmw.tmb_server.entities.enums.Authority;
import lombok.Data;

import java.util.List;

@Data
public class UserRegisterDto {

    private int employeeNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Authority> authorities;

}
