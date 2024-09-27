package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.domain.pojo.Carousel;
import com.example.springboot.service.CarouselService;
import com.example.springboot.mapper.CarouselMapper;
import org.springframework.stereotype.Service;

/**
* @author 86151
* @description 针对表【carousel】的数据库操作Service实现
* @createDate 2024-09-27 00:07:18
*/
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel>
    implements CarouselService{

}




