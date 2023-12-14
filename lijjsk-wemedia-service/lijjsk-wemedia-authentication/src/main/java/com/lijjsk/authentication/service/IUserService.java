package com.lijjsk.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.authentication.pojo.User;
import com.lijjsk.model.wemedia.user.dtos.*;

import java.util.List;
import java.util.Map;

public interface IUserService extends IService<User> {

    /**
     * 登录服务
     */
    Map<String,Object> login(UserRequestDto userRequestDto);

    /**
     * 权限认定服务，供远程调用
     */
    Boolean authority_judge(String token, String uri);

}
