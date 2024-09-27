package com.example.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constant;
import com.example.springboot.common.PageQuery;
import com.example.springboot.common.R;
import com.example.springboot.domain.dto.*;
import com.example.springboot.domain.pojo.Carousel;
import com.example.springboot.domain.pojo.Community;
import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.domain.pojo.UserRole;
import com.example.springboot.domain.query.CommunityQuery;
import com.example.springboot.domain.query.MessageQuery;
import com.example.springboot.domain.query.UserQuery;
import com.example.springboot.domain.vo.MessageVO;
import com.example.springboot.security.MyUserDetailServiceImpl;
import com.example.springboot.service.*;
import com.example.springboot.utils.BeanUtils;
import com.example.springboot.utils.CollUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final MyUserDetailServiceImpl myUserDetailService;

    private final CommunityService communityService;

    private final MessageService messageService;

    private final CarouselService carouselService;

    private final UserRoleService userRoleService;

    private static final String ROLE_ADMIN = "ROLE_admin";

    private static final String LOAD_PASS = "123";


    @Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;


    @PostMapping("/login")
    public R adminLogin(LoginDTO dto){
        SUser sUser = userService.userLogin(dto);
        return R.ok().put("user",sUser);
    }

    @PostMapping("/com")
    public R communityList(CommunityQuery query){
        return R.ok().put("page",communityService.communityPage(query));
    }

    @PostMapping("/user")
    public R userList(UserQuery query){
        return R.ok().put("page",userService.userList(query));
    }

    @DeleteMapping("/delete/{id}")
    public R deleteUser(@PathVariable Long id){
        userService.removeById(id);
        return R.ok();
    }

    @PostMapping("/load/{id}")
    public R loadUser(@PathVariable Long id){
        boolean update = userService.lambdaUpdate()
                .eq(SUser::getId, id)
                .set(SUser::getPassword, bcryptPasswordEncoder.encode(LOAD_PASS))
                .update();
        if (!update){
            return R.error("系统错误！");
        }
        return R.ok();
    }

    @PostMapping("/page")
    public R adminList(PageQuery query){
        Page<SUser> page = userService.lambdaQuery()
                .inSql(SUser::getId, "select user_id from user_role where role_id = 1 ")
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        if (CollUtils.isEmpty(page.getRecords())){
            return R.ok().put("page", PageDTO.empty(page));
        }
        return R.ok().put("page", PageDTO.of(page));
    }

    /**
     * 添加社团
     * @param file
     * @param dto
     * @return
     */
    @PostMapping("/addon")
    public R addCommunity(@RequestPart("file") MultipartFile file,CommunityDTO dto){
        String originalFilename = file.getOriginalFilename();
        String type = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + type;
        File target = new File(Constant.IMAGE_STATIC);
        if (!target.exists()) {
            target.mkdirs();
        }
        File saveFile = new File(target, fileName);
        try {
            file.transferTo(saveFile);
            dto.setAvater(Constant.IMAGE_URL + fileName);
            Community community = new Community();
            community.setAvater(dto.getAvater());
            community.setName(dto.getName());
            community.setRemark(dto.getRemark());
            communityService.getBaseMapper().insert(community);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统错误！！！");
        }
        return R.ok();
    }

    /**
     * 删除社团
     * @param id
     * @return
     */
    @DeleteMapping("/deletion/{id}")
    public R deleteCommunity(@PathVariable Integer id){
        boolean b = communityService.removeById(id);
        if (!b){
            return R.error("系统错误！！！");
        }
        return R.ok();
    }

    /**
     * 获取数据
     */
    @GetMapping("/com/{id}")
    public R getCommunity(@PathVariable Integer id){
        Community community = communityService.getById(id);
        return R.ok().put("com",community);
    }

    /**
     * 修改社团数据
     */
    @PostMapping("/updateCom")
    public R updateCommunity(@RequestPart(value = "file",required = false) MultipartFile file, CommunityDTO dto){
        String fileName = "";
        if (file != null){
            String originalFilename = file.getOriginalFilename();
            String type = originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName = UUID.randomUUID() + type;
            File target = new File(Constant.IMAGE_STATIC);
            if (!target.exists()) {
                target.mkdirs();
            }
            File saveFile = new File(target, fileName);
        try {
            file.transferTo(saveFile);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统错误！！！");
        }
        }
        if (file != null){
            dto.setAvater(Constant.IMAGE_URL + fileName);
        }
        Community community = new Community();
        BeanUtils.copyProperties(dto,community);
        communityService.updateById(community);
        return R.ok();
    }

    /**
     *动态分页
     */
    @PostMapping("/message")
    public R messageQuery(MessageQuery query){
        PageDTO<MessageVO> page = messageService.backmessagePage(query);
        return R.ok().put("page",page);
    }

    /**
     * 删除动态
     * @param id
     * @return
     */
    @DeleteMapping("/message/delete/{id}")
    public R deleteMessage(@PathVariable Long id){
        boolean b = messageService.removeById(id);
        if (!b){
            return R.error("系统错误！！！");
        }
        return R.ok();
    }

    /**
     *添加轮播图
     */
    @PostMapping("/updateCarousel")
    public R updateCarousel(@RequestPart(value = "file",required = false) MultipartFile file, CarouselDTO dto){
        String fileName = "";
        if (file != null){
            String originalFilename = file.getOriginalFilename();
            String type = originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName = UUID.randomUUID() + type;
            File target = new File(Constant.IMAGE_STATIC);
            if (!target.exists()) {
                target.mkdirs();
            }
            File saveFile = new File(target, fileName);
            try {
                file.transferTo(saveFile);
            } catch (Exception e) {
                e.printStackTrace();
                return R.error("系统错误！！！");
            }
        }
        if (file != null){
            dto.setImage(Constant.IMAGE_URL + fileName);
        }
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(dto,carousel);
        carouselService.updateById(carousel);
        return R.ok();
    }

    /**
     * 添加轮播图
     * @param file
     * @param dto
     * @return
     */
    @PostMapping("/carousel/addon")
    public R addCarousel(@RequestPart("file") MultipartFile file,CarouselDTO dto){
        String originalFilename = file.getOriginalFilename();
        String type = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + type;
        File target = new File(Constant.IMAGE_STATIC);
        if (!target.exists()) {
            target.mkdirs();
        }
        File saveFile = new File(target, fileName);
        try {
            file.transferTo(saveFile);
            dto.setImage(Constant.IMAGE_URL + fileName);
            Carousel carousel = new Carousel();
            carousel.setImage(dto.getImage());
            carousel.setUrl(dto.getUrl());
            carousel.setType(1);
            carouselService.getBaseMapper().insert(carousel);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统错误！！！");
        }
        return R.ok();
    }

    /**
     * 删除轮播图
     */
    @DeleteMapping("/carousel/delete/{id}")
    public R deleteCarousel(@PathVariable Integer id){
        boolean b = carouselService.removeById(id);
        if (!b){
            return R.error("系统错误！！！");
        }
        return R.ok();
    }

    /**
     * 获取主页轮播图
     */
    @PostMapping("/carousel")
    public R carouselList(Integer type){
        List<Carousel> list = carouselService.list(new LambdaQueryWrapper<Carousel>().eq(Carousel::getType, type));
        return R.ok().put("list",list);
    }

    @GetMapping("/carousel/{id}")
    public R getCarousel(@PathVariable Integer id){
        Carousel carousel = carouselService.getById(id);
        return R.ok().put("carousel",carousel);
    }


    /**
     * 添加管理员
     */
    @PostMapping("/addAdmin")
    public R addAdmin(AdminDTO dto){
        SUser user = new SUser();
        user.setUsername(dto.getUsername());
        user.setPassword(bcryptPasswordEncoder.encode(LOAD_PASS));
        user.setAvater(null);
        user.setStudentId(null);
        user.setClassName(null);
        user.setPhone(dto.getPhone());
        user.setRemark(null);
        user.setCommunityId(null);
        boolean save = userService.save(user);
        if (!save){
            return R.error("系统错误！！！");
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(1);
        int insert = userRoleService.getBaseMapper().insert(userRole);
        if (insert <= 0){
            return R.error("系统错误！！！");
        }
        return R.ok();
    }

}
