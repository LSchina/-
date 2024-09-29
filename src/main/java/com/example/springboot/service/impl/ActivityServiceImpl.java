package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.domain.pojo.Activity;
import com.example.springboot.service.ActivityService;
import com.example.springboot.mapper.ActivityMapper;
import org.springframework.stereotype.Service;

/**
* @author 86151
* @description 针对表【activity】的数据库操作Service实现
* @createDate 2024-09-29 00:36:51
*/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService{

}




