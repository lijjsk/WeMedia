package com.lijjsk.authentication.controller;


import com.lijjsk.authentication.mapper.MenuMapper;
import com.lijjsk.authentication.service.IUserService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.wemedia.user.dtos.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authenticate")
public class UserController {
    @Resource
    MenuMapper menuMapper;
    @Resource
    IUserService userService;
    //================================================================================用户通用方法

    /**
     * 登录方法
     * @param userRequestDto
     */
    @PostMapping("/login")
    public ResponseResult Login(@RequestBody UserRequestDto userRequestDto) {
        Map<String,Object> userInfo = userService.login(userRequestDto);

        if(userInfo.get("token")!=null){
            return new ResponseResult(200,"登录成功",userInfo);
        }else {
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
}
