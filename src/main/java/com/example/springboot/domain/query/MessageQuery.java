package com.example.springboot.domain.query;

import com.example.springboot.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class MessageQuery extends PageQuery {

    private String title;

}