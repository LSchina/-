package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.domain.pojo.UserRole;
import com.example.springboot.service.UserRoleService;
import com.example.springboot.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 86151
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2024-09-27 13:02:37
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




