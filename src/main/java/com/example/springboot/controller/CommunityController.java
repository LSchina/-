package com.example.springboot.controller;


import com.example.springboot.common.R;
import com.example.springboot.domain.dto.PageDTO;
import com.example.springboot.domain.pojo.Community;
import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.domain.query.CommunityQuery;
import com.example.springboot.service.CommunityService;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/communities")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    private final UserService userService;

    @GetMapping("/list")
    public R communityList(){
        return R.ok().put("list",communityService.list());
    }

    @PostMapping("/pageList")
    public R communityPage(CommunityQuery query){
        PageDTO<Community> page = communityService.communityPage(query);
        return R.ok().put("page",page);
    }

    @GetMapping("/intro/{id}")
    public R communityIntro(@PathVariable Integer id){
        Community community = communityService.getById(id);
        return R.ok().put("community",community);
    }

    @GetMapping("/getcom")
    public R communityOne(){
        Long userId = UserContext.getUser();
        SUser user = userService.getById(userId);
        Integer communityId = user.getCommunityId();
        Community community = communityService.getById(communityId);
        return R.ok().put("community",community);
    }
}
