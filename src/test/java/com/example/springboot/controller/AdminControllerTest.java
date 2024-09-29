package com.example.springboot.controller;

import cn.hutool.core.date.DateTime;
import com.example.springboot.domain.pojo.MessageRecord;
import com.example.springboot.service.MessageRecordService;
import com.example.springboot.utils.NumberUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private MessageRecordService recordService;

    @Test
    void count() {
        int num = 7;
        List<Integer> count = new ArrayList<>();
        List<String> date = new ArrayList<>();
        LocalDate now1 = LocalDate.now();
        for (int i = 0; i < num; i++) {
            Integer count1 = recordService.lambdaQuery()
                    .lt(MessageRecord::getCreateTime, now1.plus(1L, ChronoUnit.DAYS))
                    .gt(MessageRecord::getCreateTime, now1)
                    .count();
            count.add(count1);
            date.add(now1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            now1 = now1.minusDays(1);
        }
        System.out.println(count);
        System.out.println(date);
    }
}