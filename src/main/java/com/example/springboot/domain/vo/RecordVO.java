package com.example.springboot.domain.vo;

import com.example.springboot.domain.pojo.Record;
import lombok.Data;

@Data
public class RecordVO extends Record {

    private String username;

    private String className;

}
