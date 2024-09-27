package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.pojo.Community;
import com.example.springboot.domain.query.CommunityQuery;
import com.example.springboot.service.CommunityService;
import com.example.springboot.mapper.CommunityMapper;
import com.example.springboot.utils.CollUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86151
* @description 针对表【community】的数据库操作Service实现
* @createDate 2024-06-18 11:51:53
*/
@Service
@RequiredArgsConstructor
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community>
    implements CommunityService{


    @Override
    public PageDTO<Community> communityPage(CommunityQuery query) {
        Page<Community> page = lambdaQuery()
                .like(Community::getName, query.getName())
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<Community> records = page.getRecords();
        if (CollUtils.isEmpty(records)){
            return PageDTO.empty(page);
        }
        return PageDTO.of(page);
    }
}




