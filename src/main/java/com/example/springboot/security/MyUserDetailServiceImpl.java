package com.example.springboot.security;


import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailServiceImpl implements UserDetailsService {


    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("用户名异常");
        }
        SUser user = userService.lambdaQuery()
                .eq(SUser::getUsername, username)
                .one();
        if (user == null){
            throw new UsernameNotFoundException("用户不存在!!!");
        }
        return new User(user.getUsername(), user.getPassword(), getUserAuthority(user.getId()));
    }

    /**
     * 获取用户权限列表
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Long userId) {

        System.out.println("xxx");

        String authority = userService.getUserAuthorityInfo(userId);
        System.out.println("authority：" + authority);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);


    }


}
