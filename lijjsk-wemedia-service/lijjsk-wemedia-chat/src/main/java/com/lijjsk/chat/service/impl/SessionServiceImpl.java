package com.lijjsk.chat.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.chat.mapper.SessionMapper;
import com.lijjsk.chat.mapper.UserMapper;
import com.lijjsk.chat.service.SessionService;
import com.lijjsk.model.chat.dtos.SessionDto;
import com.lijjsk.model.chat.pojos.Session;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.user.pojos.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session> implements SessionService{
    @Autowired
    private SessionMapper sessionMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    @SentinelResource("getUserChatList")
    public ResponseResult getUserChatList(Integer userId) {
        List<Session> sessionList = sessionMapper.selectList(Wrappers.<Session>lambdaQuery().eq(Session::getSender,userId).or().eq(Session::getReceiver,userId));
        List<SessionDto> dtoList = new ArrayList<>();
        for (Session session : sessionList) {
            SessionDto sessionDto = new SessionDto();
            if (userId.equals(session.getSender())) {
                //当前用户是sender
                sessionDto.setSessionId(session.getId());
                //朋友是receiver
                sessionDto.setFriendId(session.getReceiver());
                User user = userMapper.selectById(sessionDto.getFriendId());
                sessionDto.setFriendName(user.getUsername());
                sessionDto.setProfilePhoto(user.getProfilePhoto());
                dtoList.add(sessionDto);
            } else {
                // 当前用户是receiver
                sessionDto.setSessionId(session.getId());
                // 朋友是sender
                sessionDto.setFriendId(session.getSender());
                User user = userMapper.selectById(sessionDto.getFriendId());
                sessionDto.setFriendName(user.getUsername());
                sessionDto.setProfilePhoto(user.getProfilePhoto());
                dtoList.add(sessionDto);
            }
        }
        return ResponseResult.okResult(dtoList);
    }
}
