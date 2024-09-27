package com.example.springboot.service;

import com.example.springboot.domain.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86151
* @description 针对表【menu】的数据库操作Service
* @createDate 2024-06-18 13:04:18
*/
public interface MenuService extends IService<Menu> {

    List<Menu> buildTreeMenu(List<Menu> sysMenuList);
}
