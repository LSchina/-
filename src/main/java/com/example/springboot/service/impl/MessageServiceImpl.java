package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.domain.dto.MessageDTO;
import com.example.springboot.domain.dto.NoticeDTO;
import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.pojo.Community;
import com.example.springboot.domain.pojo.Message;
import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.domain.query.CommunityQuery;
import com.example.springboot.domain.query.MessageQuery;
import com.example.springboot.domain.vo.MessageVO;
import com.example.springboot.enums.MessageStatus;
import com.example.springboot.service.CommunityService;
import com.example.springboot.service.MessageService;
import com.example.springboot.mapper.MessageMapper;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.BeanUtils;
import com.example.springboot.utils.CollUtils;
import com.example.springboot.utils.StringUtils;
import com.example.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 86151
 * @description 针对表【message】的数据库操作Service实现
 * @createDate 2024-06-18 13:03:54
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
        implements MessageService {

    private final UserService userService;

    private final CommunityService communityService;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void messageSave(MessageDTO dto) {
        Long userId = UserContext.getUser();
        if (StringUtils.isEmpty(dto.getComment())) {
            throw new RuntimeException("您的消息为空");
        }
        Message message = new Message();
        message.setType(0);
        message.setComment(dto.getComment());
        message.setUserId(userId);
        message.setTitle(dto.getTitle());
        message.setImage(dto.getImage());
        save(message);
    }

    @Override
    public List<MessageVO> commonList() {
        List<Message> list = lambdaQuery()
                .eq(Message::getType, 0)
                .list();
        if (CollUtils.isEmpty(list)) {
            return null;
        }
        Set<Long> collect = list.stream().map(Message::getUserId).collect(Collectors.toSet());
        List<SUser> users = userService.listByIds(collect);
        if (CollUtils.isEmpty(users)) {
            return null;
        }
        Map<Long, SUser> usersMap = users.stream().collect(Collectors.toMap(SUser::getId, item -> item));
        List<MessageVO> messageList = new ArrayList<>();

        for (Message message : list) {
            MessageVO vo = new MessageVO();
            BeanUtils.copyProperties(message, vo);
            SUser sUser = usersMap.get(message.getUserId());
            vo.setUsername(sUser.getUsername());
            vo.setAvater(sUser.getAvater());
            messageList.add(vo);
        }
        return messageList;
    }



    @Override
    @Transactional
    public PageDTO<MessageVO> messagePage(MessageQuery query) {

        Page<Message> page = lambdaQuery()
                .like(Message::getTitle, query.getTitle())
                .eq(Message::getType, 0)
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<Message> records = page.getRecords();
        if (CollUtils.isEmpty(records)) {
            PageDTO.empty(page);
        }
        Set<Long> collect = records.stream().map(Message::getUserId).collect(Collectors.toSet());
        List<SUser> users = userService.listByIds(collect);
        if (CollUtils.isEmpty(users)) {
            return null;
        }
        Map<Long, SUser> usersMap = users.stream().collect(Collectors.toMap(SUser::getId, item -> item));
        List<MessageVO> messageList = new ArrayList<>();

        for (Message message : records) {
            MessageVO vo = new MessageVO();
            BeanUtils.copyProperties(message, vo);
            SUser sUser = usersMap.get(message.getUserId());
            vo.setUsername(sUser.getUsername());
            vo.setAvater(sUser.getAvater());
            messageList.add(vo);
        }
        return PageDTO.of(page, messageList);
    }

    @Override
    public List<MessageVO> newsRecommend() {
        List<Message> list = lambdaQuery().eq(Message::getType, 2).last("limit 2").list();
        if (CollUtils.isEmpty(list)){
            return null;
        }
        Set<Integer> collect = list.stream().map(Message::getCommunityId).collect(Collectors.toSet());
        List<Community> communities = communityService.listByIds(collect);
        Map<Integer, Community> communityMap = communities.stream().collect(Collectors.toMap(Community::getId, item -> item));
        List<MessageVO> voList = new ArrayList<>();
        for (Message message : list) {
            MessageVO vo = BeanUtils.copyBean(message, MessageVO.class);
            vo.setAvater(message.getImage());
            Community community = communityMap.get(message.getCommunityId());
            vo.setCommunityName(community.getName());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public PageDTO<Message> messagePageMy(MessageQuery query) {
        Long userId = UserContext.getUser();
        Page<Message> page = lambdaQuery().like(StringUtils.isNotEmpty(query.getTitle()), Message::getTitle, query.getTitle())
                .eq(Message::getUserId, userId)
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<Message> records = page.getRecords();
        if (CollUtils.isEmpty(records)){
            return PageDTO.empty(page);
        }
        return PageDTO.of(page);
    }

    @Override
    public void addnotice(NoticeDTO dto) {
        Long userId = UserContext.getUser();
        SUser one = userService.lambdaQuery().eq(SUser::getId, userId).one();
        Message message = BeanUtils.copyBean(dto, Message.class);
        message.setType(2);
        message.setCommunityId(one.getCommunityId());
        save(message);
    }

    @Override
    public PageDTO<Message> noticeList(MessageQuery query) {
        Long userId = UserContext.getUser();
        SUser one = userService.lambdaQuery().eq(SUser::getId, userId).one();
        Page<Message> page = lambdaQuery()
                .eq(Message::getCommunityId, one.getCommunityId())
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<Message> records = page.getRecords();
        if (CollUtils.isEmpty(records)){
            return PageDTO.empty(page);
        }
        return PageDTO.of(page);

    }

    @Override
    public void findNew(NoticeDTO dto) {
        Long userId = UserContext.getUser();
        SUser one = userService.lambdaQuery().eq(SUser::getId, userId).one();
        Message message = BeanUtils.copyBean(dto, Message.class);
        message.setType(1);
        message.setCommunityId(one.getCommunityId());
        save(message);
    }

    @Override
    public PageDTO<Message> newList(CommunityQuery query) {
        List<Community> list = communityService.lambdaQuery()
                .like(StringUtils.isNotEmpty(query.getName()), Community::getName, query.getName())
                .list();
        Set<Integer> collect = list.stream().map(Community::getId).collect(Collectors.toSet());
        Page<Message> page = lambdaQuery()
                .in(Message::getCommunityId, collect)
                .eq(Message::getType,1)
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<Message> records = page.getRecords();
        if (CollUtils.isEmpty(records)){
            return PageDTO.empty(page);
        }
        return PageDTO.of(page);

    }

    @Override
    public PageDTO<MessageVO> backmessagePage(MessageQuery query) {
        Page<Message> page = lambdaQuery()
                .like(Message::getTitle, query.getTitle())
                .eq(Message::getType, 0)
                .page(query.toMpPage("create_time", false));
        List<Message> records = page.getRecords();
        if (CollUtils.isEmpty(records)) {
            PageDTO.empty(page);
        }
        Set<Long> collect = records.stream().map(Message::getUserId).collect(Collectors.toSet());
        List<SUser> users = userService.listByIds(collect);
        if (CollUtils.isEmpty(users)) {
            return null;
        }
        Map<Long, SUser> usersMap = users.stream().collect(Collectors.toMap(SUser::getId, item -> item));
        List<MessageVO> messageList = new ArrayList<>();

        for (Message message : records) {
            MessageVO vo = new MessageVO();
            BeanUtils.copyProperties(message, vo);
            SUser sUser = usersMap.get(message.getUserId());
            vo.setUsername(sUser.getUsername());
            vo.setAvater(message.getImage());
            vo.setId(message.getId());
            messageList.add(vo);
        }
        return PageDTO.of(page, messageList);
    }

    @Override
    public PageDTO<MessageVO> adminNotice(MessageQuery query) {
        Page<Message> page = lambdaQuery()
                .like(Message::getTitle, query.getTitle())
                .eq(Message::getType, query.getType())
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<Message> records = page.getRecords();
        if (CollUtils.isEmpty(records)) {
            PageDTO.empty(page);
        }
        Set<Integer> collect = records
                .stream()
                .map(Message::getCommunityId)
                .collect(Collectors.toSet());
        List<Community> communities = communityService.listByIds(collect);
        Map<Integer, String> comMap = communities
                .stream()
                .collect(Collectors.toMap(Community::getId, item -> item.getName()));
        List<MessageVO> voList = new ArrayList<>();
        for (Message record : records) {
            MessageVO vo = BeanUtils.copyBean(record, MessageVO.class);
            voList.add(vo);
            vo.setCommunityName(comMap.get(record.getCommunityId()));
        }
        return PageDTO.of(page,voList);
    }
}




