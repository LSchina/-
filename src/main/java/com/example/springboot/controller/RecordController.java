package com.example.springboot.controller;

import com.example.springboot.common.PageQuery;
import com.example.springboot.common.R;
import com.example.springboot.domain.vo.AgreeVO;
import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.dto.RecordDTO;
import com.example.springboot.domain.pojo.Record;
import com.example.springboot.domain.vo.RecordVO;
import com.example.springboot.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;


    @PostMapping("/add")
    public R add(RecordDTO dto){
        recordService.addRecord(dto);
        return R.ok();
    }

    @PostMapping("/getNew")
    public R getNew(PageQuery query){
        PageDTO<RecordVO> page = recordService.getNew(query);
        return R.ok().put("page",page);
    }

    @PostMapping("/agreeRecord")
    public R agreeRecord(AgreeVO vo){
        recordService.agreeRecord(vo);
        return R.ok();
    }

    @PostMapping("/reject/{id}")
    public R rejectRecord(@PathVariable Long id){
        boolean update = recordService.lambdaUpdate()
                .eq(Record::getId, id)
                .set(Record::getStatus, 2)
                .update();
        if (!update){
            throw new RuntimeException("拒绝异常！！！");
        }
        return R.ok();
    }

}
