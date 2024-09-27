package com.example.springboot.domain.query;

import com.example.springboot.common.PageQuery;
import lombok.Data;

@Data
public class UserQuery extends PageQuery {

    private String username;

    private String studentId;

    private String className;



}
