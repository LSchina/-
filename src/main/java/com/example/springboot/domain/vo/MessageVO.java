package com.example.springboot.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MessageVO {

    private Long id;

    private String username;

    private String comment;

    private Date createTime;

    private String avater;

    private String title;

    private String communityName;

    private String image;

}
