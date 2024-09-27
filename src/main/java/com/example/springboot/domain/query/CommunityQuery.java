package com.example.springboot.domain.query;

import com.example.springboot.common.PageQuery;
import lombok.Data;


@Data
public class CommunityQuery extends PageQuery {

    private String name;

}
