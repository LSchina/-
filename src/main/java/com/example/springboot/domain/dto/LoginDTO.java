package com.example.springboot.domain.dto;

import lombok.Data;

@Data
public class LoginDTO {

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
