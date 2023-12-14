package com.lijjsk.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.wemedia.user.dtos.FollowRequestDto;
import com.lijjsk.model.wemedia.user.dtos.UserPasswordChangeDto;
import com.lijjsk.model.wemedia.user.dtos.UserRequestDto;
import com.lijjsk.model.wemedia.user.dtos.UserResponseDto;
import com.lijjsk.user.pojo.User;

public interface IUserService extends IService<User> {

    /**
     * 用户添加，注册
     */
    Boolean addUser(User user);

    /**
     * 关注用户与取关用户，这里依靠type区分
     */
    Boolean followUser(FollowRequestDto followRequestDto, Boolean type);

    /**
     * 更新用户信息
     */
    Boolean updateUser(UserResponseDto userResponseDto);

    /**
     * 更新用户密码
     */
    Boolean updateUserPassword(UserPasswordChangeDto userPasswordChangeDto);

}
