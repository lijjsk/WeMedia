package com.lijjsk.chat.controller;

import com.lijjsk.chat.service.SessionService;
import com.lijjsk.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wemedia/chat")
public class ChatController {
    @Autowired
    private SessionService sessionService;
    @GetMapping("/get/chatList")
    public ResponseResult getUserChatList(@RequestParam("userId") Integer userId){
        return sessionService.getUserChatList(userId);
    }
}
