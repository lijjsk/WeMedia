package com.lijjsk.user.controller;


import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.wemedia.user.dtos.*;
import com.lijjsk.user.mapper.MenuMapper;
import com.lijjsk.user.pojo.Identity;
import com.lijjsk.user.pojo.User;
import com.lijjsk.user.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    MenuMapper menuMapper;
    @Resource
    IUserService userService;
    //================================================================================用户通用方法

    /**
     * 登录方法
     *
     * @param userRequestDto
     */
    @PostMapping("/login")
    public ResponseResult Login(@RequestBody UserRequestDto userRequestDto) {
        Map<String, Object> userInfo = userService.login(userRequestDto);

        if (userInfo.get("token") != null) {
            return new ResponseResult(200, "登录成功", userInfo);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
    }

    /**
     * 根据token解析得到用户权限，进行鉴权
     * token是用户身份信息，uri是访问路径
     * 用于进程间通信
     */
    @PostMapping("/authority_judge")
    public ResponseResult authority_judge(String token, String uri) {
        Boolean rs = userService.authority_judge(token, uri);
        if (rs) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.TOKEN_INVALID);
        }
    }

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
     * 用户信息编辑
     */
    @PutMapping("/update")
    public ResponseResult updateUser(@RequestBody UserResponseDto userResponseDto) {
        Boolean res = userService.updateUser(userResponseDto);
        if (res) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
        }
    }

    /**
     * 关注用户
     */
    @PutMapping("/followUser")
    public ResponseResult followUser(@RequestBody FollowRequestDto followRequestDto) {
        //type为1就是关注
        Boolean res = userService.followUser(followRequestDto, true);
        if (res) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
        }
    }

    /**
     * 取消关注
     */
    @PutMapping("/unfollowUser")
    public ResponseResult unfollowUser(@RequestBody FollowRequestDto followRequestDto) {
        //type为0就是取消
        Boolean res = userService.followUser(followRequestDto, false);
        if (res) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
        }
    }

    /**
     * 改密码
     */
    @PutMapping("/passwordChange")
    public ResponseResult changePassword(@RequestBody UserPasswordChangeDto userPasswordChangeDto) {
        Boolean res = userService.updateUserPassword(userPasswordChangeDto);
        if (res) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
    }

    /**
     * 获取用户粉丝列表
     */
    @GetMapping("/followedUserList")
    public ResponseResult getFollowedUserList(Integer userId) {
        List<UserFollowResponseDto> followedList = userService.selectUserFollowedListById(userId);
        if (followedList != null) {
            return new ResponseResult(200, "查找成功", followedList);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
        }
    }

    /**
     * 获取用户关注列表
     */
    @PostMapping("/followingUserList")
    public ResponseResult getFollowingUserList(Integer userId) {
        List<UserFollowResponseDto> followingList = userService.selectUserFollowingListById(userId);
        if (followingList != null) {
            return new ResponseResult(200, "查找成功", followingList);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
        }
    }

    //=================================================================================后台管理员使用的方法


    /**
     * 冻结用户
     */
    @PutMapping("/setUserBANNED")
    public ResponseResult setUserBANNED(Integer userId) {
        if (userService.setUserBANNED(userId)) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
    }

    /**
     * 解冻用户
     */
    @PutMapping("/setUserUnBANNED")
    public ResponseResult setUserUnBANNED(Integer userId) {
        if (userService.setUserUnBANNED(userId)) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
    }

    /**
     * 禁言用户
     */
    @PutMapping("/setUserMUTED")
    public ResponseResult setUserMUTED(Integer userId) {
        if (userService.setUserMUTED(userId)) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
    }

    /**
     * 解除禁言用户
     */
    @PutMapping("/setUserUnMUTED")
    public ResponseResult setUserUnMUTED(Integer userId) {
        if (userService.setUserUnMUTED(userId)) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
    }

    /**
     * 给用户充值密码（默认为hansome@123）
     */
    @PutMapping("/resetPassword")
    public ResponseResult resetPassword(Integer userId) {

        if (userService.resetPassword(userId)) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
    }

    /**
     * 用户获得会员权限
     */
    @PutMapping("/getVIPIdentity")
    public ResponseResult getVIPIdentity(@RequestBody UserIdentityDto userIdentityDto) {
        if (userService.getVIPIdentity(userIdentityDto.getUserId(),userIdentityDto.getIdentityId())) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
    }

    /**
     * 用户去除会员权限
     */
    @PutMapping("/removeVIPIdentity")
    public ResponseResult removeVIPIdentity(@RequestBody UserIdentityDto userIdentityDto) {
        if (userService.removeVIPIdentity(userIdentityDto.getUserId(),userIdentityDto.getIdentityId())) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
    }

    /**
     * 用户获得大会员权限
     */
    @PutMapping("/getSuperVIP")
    public ResponseResult getSuperVIP(@RequestBody UserIdentityDto userIdentityDto) {
        if (userService.getSuperVIP(userIdentityDto.getUserId(),userIdentityDto.getIdentityId())) {

            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
    }

    /**
     * 用户去除大会员权限
     */
    @PutMapping("/removeSuperVIP")
    public ResponseResult removeSuperVIP(@RequestBody UserIdentityDto userIdentityDto) {

        if (userService.removeSuperVIP(userIdentityDto.getUserId(),userIdentityDto.getIdentityId())) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
    }
    @GetMapping("/getIdentityList")
    public ResponseResult getIdentityList(){
        List<Identity> identities=userService.getIdentityList();
        if (identities!=null) {
            return new ResponseResult<>(200,"查询成功",identities);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
    }

}
