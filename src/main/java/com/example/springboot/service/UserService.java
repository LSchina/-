package com.example.springboot.service;

import com.example.springboot.domain.dto.LoginDTO;
import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.dto.UserDTO;
import com.example.springboot.domain.pojo.SUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.domain.query.UserQuery;
import com.example.springboot.domain.vo.UserVO;

/**
* @author 86151
* @description 针对表【user】的数据库操作Service
* @createDate 2024-06-18 13:03:08
*/
public interface UserService extends IService<SUser> {

    String getUserAuthorityInfo(Long userId);

    void userRegister(UserDTO dto);

    SUser userLogin(LoginDTO dto);

    PageDTO<UserVO> userList(UserQuery query);
}
