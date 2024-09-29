package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.domain.pojo.ActivityUser;
import com.example.springboot.service.ActivityUserService;
import com.example.springboot.mapper.ActivityUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86151
* @description 针对表【activity_user】的数据库操作Service实现
* @createDate 2024-09-29 00:41:31
*/
@Service
public class ActivityUserServiceImpl extends ServiceImpl<ActivityUserMapper, ActivityUser>
    implements ActivityUserService{

}




