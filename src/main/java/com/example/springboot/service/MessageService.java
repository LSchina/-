package com.example.springboot.service;

import com.example.springboot.domain.dto.MessageDTO;
import com.example.springboot.domain.dto.NoticeDTO;
import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.pojo.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.domain.query.CommunityQuery;
import com.example.springboot.domain.query.MessageQuery;
import com.example.springboot.domain.vo.MessageVO;

import java.util.List;

/**
* @author 86151
* @description 针对表【message】的数据库操作Service
* @createDate 2024-06-18 13:03:54
*/
public interface MessageService extends IService<Message> {

    void messageSave(MessageDTO dto);

    List<MessageVO> commonList();

    PageDTO<MessageVO> messagePage(MessageQuery query);

    List<MessageVO> newsRecommend();

    PageDTO<Message> messagePageMy(MessageQuery query);

    void addnotice(NoticeDTO dto);

    PageDTO<Message> noticeList(MessageQuery query);

    void findNew(NoticeDTO dto);

    PageDTO<Message> newList(CommunityQuery query);

    PageDTO<MessageVO> backmessagePage(MessageQuery query);

    PageDTO<MessageVO> adminNotice(MessageQuery query);
}
