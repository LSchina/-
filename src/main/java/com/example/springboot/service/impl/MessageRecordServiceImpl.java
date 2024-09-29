package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.domain.pojo.MessageRecord;
import com.example.springboot.service.MessageRecordService;
import com.example.springboot.mapper.MessageRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author 86151
* @description 针对表【message_record】的数据库操作Service实现
* @createDate 2024-09-28 13:52:54
*/
@Service
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecord>
    implements MessageRecordService{

}




