package com.lijjsk.user.controller;


import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.wemedia.user.dtos.FollowRequestDto;
import com.lijjsk.model.wemedia.user.dtos.UserPasswordChangeDto;
import com.lijjsk.model.wemedia.user.dtos.UserRequestDto;
import com.lijjsk.model.wemedia.user.dtos.UserResponseDto;
import com.lijjsk.user.mapper.MenuMapper;
import com.lijjsk.user.pojo.User;
import com.lijjsk.user.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    MenuMapper menuMapper;
    @Resource
    IUserService userService;

    /**
     * 用户根据信息注册
     */
    @PostMapping("/register")
    public ResponseResult addUser(@RequestBody User user) {
        Boolean res = userService.addUser(user);
        if (res) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }
    }
    /**
     * 用户修改信息
     */
    @PostMapping("/update")
    public ResponseResult updateUser(@RequestBody UserResponseDto userResponseDto) {
        Boolean res = userService.updateUser(userResponseDto);
        if (res) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
        }
    }

    /**
     *
     * 关注用户
     */
    @PostMapping("/followUser")
    public ResponseResult followUser(@RequestBody FollowRequestDto followRequestDto){
        //type为1就是关注
        Boolean res=userService.followUser(followRequestDto,true);
        if(res){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
        }
    }

    /**
     *取消关注
     */
    @PostMapping("/unfollowUser")
    public ResponseResult unfollowUser(@RequestBody FollowRequestDto followRequestDto){
        //type为0就是取消
        Boolean res=userService.followUser(followRequestDto,false);
        if(res){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
        }
    }
    /**
     * 改密码
     */
    @PostMapping("/passwordChange")
    public ResponseResult changePassword(@RequestBody UserPasswordChangeDto userPasswordChangeDto){
        Boolean res=userService.updateUserPassword(userPasswordChangeDto);
        if(res){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
    }
}
