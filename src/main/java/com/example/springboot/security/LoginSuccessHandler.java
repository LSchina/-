package com.example.springboot.security;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot.common.R;
import com.example.springboot.domain.pojo.Menu;
import com.example.springboot.domain.pojo.Role;
import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.MenuService;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 登录成功处理
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    private final UserService sysUserService;


    private final RoleService sysRoleService;

    private final MenuService sysMenuService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        String username=authentication.getName(); // 获取用户名
        boolean b = sysUserService.update(
                new UpdateWrapper<SUser>()
                        .set("login_date", new Date())
                        .eq("username", username)
        );// 更新最后登录日期
        if (!b){
            throw new RuntimeException("更新时间失败");
        }
        // 生成jwt token
        String token= JwtUtils.genJwtToken(username);

        SUser currentUser = sysUserService.lambdaQuery().eq(SUser::getUsername, username).one();


        // 获取当前用户拥有的权限菜单
        // 获取角色
        List<Role> roleList = sysRoleService.list(new QueryWrapper<Role>().inSql("id", "select role_id from user_role where user_id=" + currentUser.getId()));

        // 设置角色
        currentUser.setRoles(roleList.stream().map(Role::getName).collect(Collectors.joining(",")));

        StringBuffer permsStr=new StringBuffer();

        // 获取菜单权限
        Set<Menu> menuSet = new HashSet<Menu>();
        for(Role sysRole:roleList){
            List<Menu> sysMenuList = sysMenuService.list(new QueryWrapper<Menu>().inSql("id", "select menu_id from role_menu where role_id=" + sysRole.getId()));
            for(Menu sysMenu:sysMenuList){
                menuSet.add(sysMenu);
                permsStr.append(sysMenu.getPerms()+",");
            }
        }

        String perms[]= StringUtils.tokenizeToStringArray(permsStr.toString(),",");

        List<Menu> sysMenuList = new ArrayList<>(menuSet); // 转成集合List

        List<Menu> menuList = sysMenuService.buildTreeMenu(sysMenuList); // 构造菜单树

        outputStream.write(JSONUtil.toJsonStr(R.ok("登录成功").put("authorization",token).put("menuList",menuList).put("currentUser",currentUser).put("perms",perms)).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();
    }



}
