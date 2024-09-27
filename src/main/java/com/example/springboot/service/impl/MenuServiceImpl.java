package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.domain.pojo.Menu;
import com.example.springboot.service.MenuService;
import com.example.springboot.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 86151
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2024-06-18 13:04:18
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Override
    public List<Menu> buildTreeMenu(List<Menu> sysMenuList) {
        /**
         * 构造菜单树
         * @param sysMenuList
         * @return
         */
        List<Menu> resultMenuList = new ArrayList<>();

        for (Menu sysMenu : sysMenuList) {
            // 寻找子节点1
            for (Menu e : sysMenuList) {
                if (e.getParentId() == sysMenu.getId()) {
                    sysMenu.getChildren().add(e);
                }
            }
            // 判断父节点，添加到集合2
            if (sysMenu.getParentId() == 0L) {
                resultMenuList.add(sysMenu);
            }
        }
        return resultMenuList;
    }
}




