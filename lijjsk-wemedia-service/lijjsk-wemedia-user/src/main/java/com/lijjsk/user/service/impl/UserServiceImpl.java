package com.lijjsk.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.model.wemedia.user.dtos.UserPasswordChangeDto;
import com.lijjsk.model.wemedia.user.dtos.UserRequestDto;
import com.lijjsk.model.wemedia.user.dtos.UserResponseDto;
import com.lijjsk.user.mapper.MenuMapper;
import com.lijjsk.user.mapper.UserMapper;
import com.lijjsk.user.pojo.Identity;
import com.lijjsk.user.pojo.Menu;
import com.lijjsk.user.pojo.User;
import com.lijjsk.user.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.lijjsk.model.wemedia.user.dtos.FollowRequestDto;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Resource
    MenuMapper menuMapper;
    @Resource
    UserMapper userMapper;

    /**
     * 注册用户
     */
    @Override
    public Boolean addUser(User user) {
        //加密
        String encoderPassword = encoder.encode(user.getPassword());
        user.setPassword(encoderPassword);
        //存入
        int res = userMapper.insert(user);
        return res != 0;
    }

    /**
     * 用户信息更新，不包含密码
     */
    @Override
    public Boolean updateUser(UserResponseDto userResponseDto) {
        //存入
        Boolean res = userMapper.updateUser(userResponseDto);
        return res;
    }

    /**
     * 关注与取关用户，根据 type的值判断
     */
    @Override
    public Boolean followUser(FollowRequestDto followRequestDto, Boolean type) {
        //被关注
        Integer following_id = followRequestDto.getFollowing_id();
        //主动关注
        Integer followed_id = followRequestDto.getFollowed_id();
        Boolean res1;
        if (type) {
            //type=true
            res1 = userMapper.followUser(following_id, followed_id);
        } else {
            res1 = userMapper.unfollowUser(following_id, followed_id);
        }

        log.info("关注记录添加成功===================》被关注{},主动关注{}", followed_id, following_id);
        //被关注用户，粉丝加一
        Boolean res2 = userMapper.updateUserByFollowedId(followed_id, type);
        log.info("被关注用户粉丝加一===================》被关注{}", followed_id);
        //关注别人的用户关注人数加一
        Boolean res3 = userMapper.updateUserByFollowingId(following_id, type);
        log.info("主动关注用户粉丝加一===================》被关注{}", following_id);

        return res1 & res2 & res3;
    }

    /**
     * 更新用户密码
     */
    public Boolean updateUserPassword(UserPasswordChangeDto userPasswordChangeDto) {
        String prePassword = userPasswordChangeDto.getPrePassword();
        String sufPassword = userPasswordChangeDto.getSufPassword();
        Integer userId = userPasswordChangeDto.getId();
        log.info("改变前的密码：======================》{}", prePassword);
        log.info("要改为密码：======================》{}", sufPassword);
        //将密码比对
        String checkPassword = userMapper.selectUserPasswordById(userId);
        log.info("查出来的用户密码=========================>{}", checkPassword);
        if (encoder.matches(prePassword, checkPassword)) {
            String nextPassword = encoder.encode(prePassword);
            return userMapper.updateUserPasswordById(nextPassword, userId);
        }
        return false;
    }
}
