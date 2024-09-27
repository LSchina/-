package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.domain.pojo.Role;
import com.example.springboot.service.RoleService;
import com.example.springboot.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 86151
* @description 针对表【role】的数据库操作Service实现
* @createDate 2024-06-18 13:04:09
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




