package com.lijjsk.chat.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/wemedia/chat")
public class ChatController {
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
