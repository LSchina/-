package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.PageQuery;
import com.example.springboot.domain.vo.AgreeVO;
import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.dto.RecordDTO;
import com.example.springboot.domain.pojo.Record;
import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.domain.vo.RecordVO;
import com.example.springboot.service.RecordService;
import com.example.springboot.mapper.RecordMapper;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.BeanUtils;
import com.example.springboot.utils.CollUtils;
import com.example.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 86151
* @description 针对表【record】的数据库操作Service实现
* @createDate 2024-07-01 03:57:24
*/
@Service
@RequiredArgsConstructor
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record>
    implements RecordService{

    private final UserService userService;

    @Override
    public void addRecord(RecordDTO message) {
        Long userId = UserContext.getUser();
        SUser one = userService.lambdaQuery().eq(SUser::getId, userId).one();
        if (!(one.getCommunityId() == null)){
            throw new RuntimeException("您已经加入社团,不能在进行申请");
        }
        Record record = new Record();
        record.setUserId(userId);
        record.setMessageId(message.getId());
        record.setCommunityId(message.getCommunityId());
        record.setStatus(1);
        save(record);
    }

    @Override
    public PageDTO<RecordVO> getNew(PageQuery query) {
        Long userId = UserContext.getUser();
        SUser one = userService.lambdaQuery().eq(SUser::getId, userId).one();
        Page<Record> page = lambdaQuery()
                .eq(Record::getStatus,1)
                .eq(Record::getCommunityId, one.getCommunityId())
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<Record> records = page.getRecords();
        if (CollUtils.isEmpty(records)){
            return PageDTO.empty(page);
        }
        Set<Long> collect = records.stream().map(Record::getUserId).collect(Collectors.toSet());

        List<SUser> list = userService.lambdaQuery().in(SUser::getId, collect).list();
        Map<Long, SUser> userMap = list.stream().collect(Collectors.toMap(SUser::getId, item -> item));
        List<RecordVO> voList = new ArrayList<>();
        for (Record record : records) {
            RecordVO vo = BeanUtils.copyBean(record, RecordVO.class);
            voList.add(vo);
            SUser sUser = userMap.get(record.getUserId());
            vo.setClassName(sUser.getClassName());
            vo.setUsername(sUser.getUsername());
        }
        return PageDTO.of(page,voList);
    }

    @Override
    @Transactional
    public void agreeRecord(AgreeVO vo) {
        boolean update = lambdaUpdate()
                .eq(Record::getId, vo.getId())
                .set(Record::getStatus, 2)
                .update();
        if (!update){
            throw new RuntimeException("同意异常！！！");
        }
        boolean b = userService.lambdaUpdate()
                .eq(SUser::getId, vo.getUserId())
                .set(SUser::getCommunityId, vo.getCommunityId())
                .update();
        if (!b){
            throw new RuntimeException("同意异常！！！");
        }
    }
}




