package com.example.springboot.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MessageRecordVO {

    private Long id;

    private String comment;

    private String username;

    private Date createTime;

    private String avatar;

}
