package com.example.springboot.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Constant;
import com.example.springboot.domain.dto.LoginDTO;
import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.dto.UserDTO;
import com.example.springboot.domain.pojo.Community;
import com.example.springboot.domain.pojo.Menu;
import com.example.springboot.domain.pojo.Role;
import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.domain.query.UserQuery;
import com.example.springboot.domain.vo.UserVO;
import com.example.springboot.mapper.MenuMapper;
import com.example.springboot.mapper.RoleMapper;
import com.example.springboot.service.CommunityService;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.BeanUtils;
import com.example.springboot.utils.CollUtils;
import com.example.springboot.utils.RedisUtil;
import com.example.springboot.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 86151
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-16 16:55:08
*/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, SUser>
    implements UserService{

    private static final String PASS_SALT = "lishuo";

    private final RoleService sysRoleService;

    private final CommunityService communityService;

    @Autowired
    BCryptPasswordEncoder byCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public SUser userLogin(LoginDTO dto) {
        if (StringUtils.isEmpty(dto.getUsername()) && StringUtils.isEmpty(dto.getPassword())){
            throw new RuntimeException("账号或密码为空");
        }
        SUser one = lambdaQuery()
                .eq(SUser::getUsername, dto.getUsername())
                .one();
        if (one == null){
            throw new RuntimeException("该账号不存在");
        }
        // 密码加密进行比较
        if (!byCryptPasswordEncoder().matches(dto.getPassword(), one.getPassword())){
            throw new RuntimeException("密码错误,请重新登录");
        }
        // 获取当前用户拥有的权限菜单
        // 获取角色
        List<Role> roleList = sysRoleService.list(new QueryWrapper<Role>().inSql("id", "select role_id from user_role where user_id=" + one.getId()));
        // 设置角色
        String collect = roleList.stream().map(Role::getCode).collect(Collectors.joining(","));
        if (collect.contains("admin")){
            one.setRoles("admin");
            return one;
        }else{
            throw new RuntimeException("该账号没有权限");
        }
    }

    @Override
    public PageDTO<UserVO> userList(UserQuery query) {
        Set<Long> collect0 = new HashSet<>();
        if (StringUtils.isNotEmpty(query.getClassName())){
            collect0 = lambdaQuery()
                    .like(SUser::getClassName, query.getClassName())
                    .list()
                    .stream().map(SUser::getId).collect(Collectors.toSet());
        }
        Set<Long> collect1 = new HashSet<>();
        if (StringUtils.isNotEmpty(query.getStudentId())){
            collect1 = lambdaQuery()
                    .like(SUser::getStudentId, query.getStudentId())
                    .list()
                    .stream().map(SUser::getId).collect(Collectors.toSet());
        }
        Set<Long> collect3 = new HashSet<>();
        if (StringUtils.isNotEmpty(query.getUsername())){
            collect3 = lambdaQuery()
                    .like(SUser::getUsername, query.getUsername())
                    .list()
                    .stream().map(SUser::getId).collect(Collectors.toSet());
        }
        Page<SUser> page = lambdaQuery()
                .in(StringUtils.isNotEmpty(query.getUsername())
                        , SUser::getId, collect3)
                .in(StringUtils.isNotEmpty(query.getClassName())
                        , SUser::getId,collect0)
                .in(StringUtils.isNotEmpty(query.getStudentId())
                        , SUser::getId, collect1)
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<SUser> records = page.getRecords();
        if (CollUtils.isEmpty(records)) {
            return PageDTO.empty(page);
        }
        Set<Integer> idset = records.stream().map(item -> item.getCommunityId()).collect(Collectors.toSet());
        List<Community> list = communityService.lambdaQuery().in(Community::getId, idset).list();
        Map<Integer, String> collect = list.stream().collect(Collectors.toMap(Community::getId, item -> item.getName()));
        Set<Long> userIdSet = records.stream().map(SUser::getId).collect(Collectors.toSet());
        List<UserVO> voList = new ArrayList<>();
        for (SUser record : records) {
            UserVO vo = BeanUtils.copyBean(record, UserVO.class);
            voList.add(vo);
            vo.setCommunityName(collect.get(record.getCommunityId()));
        }
        return PageDTO.of(page, voList);
    }

    //输入的密码转化为MD5加密后的密码的自定义方法
    private String securePass(String password){
        return SecureUtil.md5(password + PASS_SALT);
    }


    private final RoleMapper roleMapper;

    private final MenuMapper menuMapper;

    private final RedisUtil redisUtil;
    @Override
    public String getUserAuthorityInfo(Long userId) {
        StringBuffer authority=new StringBuffer();

        if(redisUtil.hasKey(Constant.AUTHORITY_KEY+userId)){
            System.out.println("有缓存");
            authority.append(redisUtil.get(Constant.AUTHORITY_KEY,String.valueOf(userId)));
        }else{
            System.out.println("没缓存");
            // 获取角色
            List<Role> roleList = roleMapper.selectList(new QueryWrapper<Role>().inSql("id", "select role_id from user_role where user_id=" + userId));

            if(roleList.size()>0){
                String roleCodeStrs=roleList.stream().map(r->"ROLE_"+r.getCode()).collect(Collectors.joining(","));
                authority.append(roleCodeStrs);
            }

            // 获取菜单权限
            Set<String> menuCodeSet = new HashSet<String>();
            for(Role role:roleList){
                List<Menu> sysMenuList = menuMapper.selectList(new QueryWrapper<Menu>().inSql("id", "select menu_id from role_menu where role_id=" + role.getId()));
                for(Menu sysMenu:sysMenuList){
                    String perms=sysMenu.getPerms();
                    if(StringUtils.isNotEmpty(perms)){
                        menuCodeSet.add(perms);
                    }
                }
            }
            if(menuCodeSet.size()>0){
                authority.append(",");
                String menuCodeStrs = menuCodeSet.stream().collect(Collectors.joining(","));
                authority.append(menuCodeStrs);
            }
            redisUtil.set(Constant.AUTHORITY_KEY,String.valueOf(userId),authority,10*60);
            System.out.println("authority:"+authority.toString());
        }
        return authority.toString();
    }
    //用户进行注册
    @Override
    public void userRegister(UserDTO dto) {
        if (dto == null){
            throw new RuntimeException("注册信息不能为空");
        }
        SUser user = new SUser();
        user.setUsername(dto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setStudentId(dto.getStudentId());
        user.setPhone(dto.getPhone());
        user.setClassName(dto.getClassName());
        save(user);
    }


}




