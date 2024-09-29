package com.example.springboot.controller;

import com.example.springboot.common.R;
import com.example.springboot.domain.dto.MessageRecordDTO;
import com.example.springboot.domain.pojo.MessageRecord;
import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.domain.vo.MessageRecordVO;
import com.example.springboot.service.MessageRecordService;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.StringUtils;
import com.example.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messagerecord")
@RequiredArgsConstructor
public class MessageRecordController {


    private final MessageRecordService recordService;

    private final UserService userService;

    @PostMapping("/add")
    public R messageRecordAdd(MessageRecordDTO dto){
        if (StringUtils.isEmpty(dto.getComment())){
            return R.error("评论内容不能为空");
        }
        MessageRecord record = new MessageRecord();
        record.setComment(dto.getComment());
        record.setMessageId(dto.getMessageId());
        Long userId = UserContext.getUser();
        SUser user = userService.getById(userId);
        record.setUserId(userId);
        record.setUsername(user.getUsername());
        record.setParentId(0L);
        boolean save = recordService.save(record);
        if (!save){
            return R.error("系统错误！！！");
        }
        return R.ok();
    }


    @PostMapping("/{id}")
    public R messageRecordList(@PathVariable Long id){
        List<MessageRecord> list = recordService.lambdaQuery().eq(MessageRecord::getMessageId, id).list();
        List<MessageRecord> collect = list
                .stream()
                .filter(record -> record.getParentId() == null || record.getParentId().equals(0L))
                .collect(Collectors.toList());
        Set<Long> userIdSet = collect.stream().map(MessageRecord::getUserId).collect(Collectors.toSet());
        List<SUser> userList = userService.lambdaQuery().in(SUser::getId, userIdSet).list();
        Map<Long, SUser> userMap = userList.stream().collect(Collectors.toMap(SUser::getId, item -> item));
        List<MessageRecordVO> voList = new ArrayList<>();
        for (MessageRecord messageRecord : collect) {
            MessageRecordVO vo = new MessageRecordVO();
            voList.add(vo);
            vo.setId(messageRecord.getId());
            vo.setAvatar(userMap.get(messageRecord.getUserId()).getAvater());
            vo.setUsername(messageRecord.getUsername());
            vo.setComment(messageRecord.getComment());
            vo.setCreateTime(messageRecord.getCreateTime());
        }
        return R.ok().put("list",voList);

    }

}
