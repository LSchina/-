package com.example.springboot.domain.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String password;
    private String newPass;
    private String studentId;
    private String className;
    private String phone;
}
