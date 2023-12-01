package com.lijjsk.wemedia.login.controller;


import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.dtos.UserRequestDto;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.wemedia.login.pojo.User;
import com.lijjsk.wemedia.login.service.IUserService;
import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    IUserService userService;
    @PostMapping("/login")
    public ResponseResult Login(@RequestBody UserRequestDto userRequestDto){
        String token=userService.login(userRequestDto);
        return ResponseResult.okResult(token);
    }
    @GetMapping("/searchUserTest")
    public ResponseResult test(){
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
