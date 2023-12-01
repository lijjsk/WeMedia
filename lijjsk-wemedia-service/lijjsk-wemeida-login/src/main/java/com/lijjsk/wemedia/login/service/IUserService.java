package com.lijjsk.wemedia.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.UserRequestDto;
import com.lijjsk.wemedia.login.pojo.User;


public interface IUserService extends IService<User> {


    String login(UserRequestDto userRequestDto);
}
