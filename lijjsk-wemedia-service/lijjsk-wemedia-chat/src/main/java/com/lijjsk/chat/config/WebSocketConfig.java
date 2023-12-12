package com.lijjsk.chat.config;

import com.lijjsk.chat.handler.ChatWebSocketHandler;
import com.lijjsk.chat.mapper.SessionMapper;
import com.lijjsk.chat.mapper.SessionMessageMapper;
import com.lijjsk.model.chat.pojos.Session;
import com.lijjsk.model.chat.pojos.SessionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info("测试连接");
        registry.addHandler(chatWebSocketHandler, "/chat")
                .setAllowedOriginPatterns("*"); // 允许跨域，允许所有来源
    }
}