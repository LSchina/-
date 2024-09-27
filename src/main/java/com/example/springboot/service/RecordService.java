package com.example.springboot.service;

import com.example.springboot.common.PageQuery;
import com.example.springboot.domain.vo.AgreeVO;
import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.dto.RecordDTO;
import com.example.springboot.domain.pojo.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.domain.vo.RecordVO;

/**
* @author 86151
* @description 针对表【record】的数据库操作Service
* @createDate 2024-07-01 03:57:24
*/
public interface RecordService extends IService<Record> {

    void addRecord(RecordDTO dto);

    PageDTO<RecordVO> getNew(PageQuery query);

    void agreeRecord(AgreeVO vo);
}
