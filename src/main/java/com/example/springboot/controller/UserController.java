package com.example.springboot.controller;


import com.example.springboot.common.Constant;
import com.example.springboot.common.R;
import com.example.springboot.domain.dto.UserDTO;
import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public R userRegister(UserDTO dto){
        userService.userRegister(dto);
        return R.ok();
    }

    /**
     * 上传头像
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/avatar")
    public R userAvatar(@RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //判断类型
        String type = originalFilename.substring(originalFilename.lastIndexOf("."));
        List<String> list = Arrays.asList(".jpg", ".png");
        if (!list.contains(type)){
            return R.error("文件类型错误");
        }
        String fileName = UUID.randomUUID() + type;
        File myFile = new File(Constant.IMAGE_STATIC);
        if (!myFile.exists()) {
            myFile.mkdirs();
        }
        File saveFile = new File(myFile, fileName);

        //保存图片
        file.transferTo(saveFile);
        Long userId = UserContext.getUser();
        String imageUrl = Constant.IMAGE_URL + fileName;
        boolean update = userService.lambdaUpdate()
                .eq(SUser::getId, userId)
                .set(SUser::getAvater, imageUrl)
                .update();
        if (!update){
            return R.error("系统错误！");
        }
        return R.ok(imageUrl);
    }
}
