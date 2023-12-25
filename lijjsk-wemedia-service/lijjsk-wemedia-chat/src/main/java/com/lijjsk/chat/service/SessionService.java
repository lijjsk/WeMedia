package com.lijjsk.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.chat.pojos.Session;
import com.lijjsk.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface SessionService extends IService<Session> {
    public ResponseResult getUserChatList(Integer userId);
}
