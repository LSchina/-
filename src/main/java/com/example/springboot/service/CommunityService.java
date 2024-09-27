package com.example.springboot.service;

import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.pojo.Community;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.domain.query.CommunityQuery;

/**
* @author 86151
* @description 针对表【community】的数据库操作Service
* @createDate 2024-06-18 11:51:53
*/
public interface CommunityService extends IService<Community> {

    PageDTO<Community> communityPage(CommunityQuery query);
}
