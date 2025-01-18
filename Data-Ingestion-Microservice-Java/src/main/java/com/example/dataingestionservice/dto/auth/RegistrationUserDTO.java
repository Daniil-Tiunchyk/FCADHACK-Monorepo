package com.example.dataingestionservice.dto.auth;


import com.example.dataingestionservice.models.Role;
import lombok.Data;

@Data
public class RegistrationUserDTO {

    private String username;
    private String password;
    private String confirmPassword;
    private Role role;

}