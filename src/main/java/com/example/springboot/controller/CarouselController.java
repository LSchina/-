package com.example.springboot.controller;


import com.example.springboot.common.R;
import com.example.springboot.domain.pojo.Carousel;
import com.example.springboot.service.CarouselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carousel")
@RequiredArgsConstructor
public class CarouselController {

    private final CarouselService carouselService;

    @PostMapping("/get")
    public R getCarousel(Integer type){
        List<Carousel> list = carouselService.lambdaQuery().eq(Carousel::getType, type).list();
        return R.ok().put("list",list);
    }

}
