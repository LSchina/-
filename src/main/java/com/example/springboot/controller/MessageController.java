package com.example.springboot.controller;

import cn.hutool.core.annotation.AliasAnnotationPostProcessor;
import com.example.springboot.common.Constant;
import com.example.springboot.common.R;
import com.example.springboot.domain.dto.MessageDTO;
import com.example.springboot.domain.dto.NoticeDTO;
import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.pojo.Message;
import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.domain.query.CommunityQuery;
import com.example.springboot.domain.query.MessageQuery;
import com.example.springboot.domain.vo.MessageVO;
import com.example.springboot.service.MessageService;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private final UserService userService;

    @GetMapping("/list")
    public R messageList(){
        return R.ok().put("list",messageService.list());
    }

    @GetMapping("/commonList")
    public R commonList(){
        List<MessageVO> list = messageService.commonList();
        return R.ok().put("list",list);
    }

    @PostMapping("/query")
    public R messageQuery(MessageQuery query){
        PageDTO<MessageVO> messagePage = messageService.messagePage(query);
        return R.ok().put("pageList",messagePage);
    }


    @PostMapping("/saveMe")
    public void messageSave(@RequestPart("file") MultipartFile file, MessageDTO dto){
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        dto.setImage(Constant.IMAGE_URL + fileName);
        messageService.messageSave(dto);
    }

    @GetMapping("/newsRec")
    public R newsRecommend(){
        List<MessageVO> list = messageService.newsRecommend();
        return R.ok().put("newsR",list);
    }

    @PostMapping("/queryMy")
    public R messageQueryMy(MessageQuery query){
        log.info("{}",query.getPageNo(),query.getPageSize(),query.getTitle());
        PageDTO<Message> page = messageService.messagePageMy(query);
        return R.ok().put("page",page);
    }

    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Long id){
        messageService.removeById(id);
        return R.ok();
    }

    /**
     * 获取信息详情
     * @param id
     * @return
     */
    @PostMapping("/getIntro/{id}")
    public R getIntro(@PathVariable Long id){
        Message message = messageService.getById(id);
        return R.ok().put("message",message);
    }

    @PostMapping("/addnotice")
    public R addnotice(NoticeDTO dto){
        messageService.addnotice(dto);
        return R.ok();
    }
    @PostMapping("/noticeList")
    public R noticeList(MessageQuery query){
        PageDTO<Message> page = messageService.noticeList(query);
        return R.ok().put("page",page);
    }

    @PostMapping("/findNew")
    public R findNew(NoticeDTO dto){
        messageService.findNew(dto);
        return R.ok();
    }

    @PostMapping("/newList")
    public R newList(CommunityQuery query){
        PageDTO<Message> page = messageService.newList(query);
        return R.ok().put("page",page);
    }

    @GetMapping("/getNew/{id}")
    public R getNew(@PathVariable Long id){
        Message message = messageService.getById(id);
        return R.ok().put("message",message);
    }

    /**
     * 获取动态详情
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable Long id){
        Message message = messageService.getById(id);
        SUser user = userService.getById(message.getUserId());
        MessageVO messageVO = BeanUtils.copyBean(message, MessageVO.class);
        messageVO.setUsername(user.getUsername());
        messageVO.setAvater(user.getAvater());
        return R.ok().put("message",messageVO);
    }

}
