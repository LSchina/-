package com.example.springboot.controller;

import com.example.springboot.common.R;
import com.example.springboot.domain.pojo.Activity;
import com.example.springboot.domain.pojo.ActivityUser;
import com.example.springboot.service.ActivityService;
import com.example.springboot.service.ActivityUserService;
import com.example.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class ActivityController {


    private final ActivityUserService activityUserService;

    private final ActivityService activityService;

    /**
     * 用户参加活动
     * @param id
     * @return
     */
    @PostMapping("/takeIn/{id}")
    public R takeActivity(@PathVariable Long id){
        Long userId = UserContext.getUser();
        List<ActivityUser> list = activityUserService.lambdaQuery().eq(ActivityUser::getActivityId, id).list();
        Set<Long> collect = list.stream().map(item -> item.getUserId()).collect(Collectors.toSet());
        if (collect.contains(userId)){
            return R.error("您已经参与过该活动了！！！");
        }
        ActivityUser activityUser = new ActivityUser();
        activityUser.setActivityId(id);
        activityUser.setUserId(userId);
        boolean save = activityUserService.save(activityUser);
        if (!save){
            return R.error("系统错误！！！");
        }
        boolean update = activityService.lambdaUpdate()
                .eq(Activity::getId, id)
                .setSql("num = num + 1").update();
        if(!update){
            return R.error("系统错误！！！");
        }
        return R.ok("参加成功");
    }


}
