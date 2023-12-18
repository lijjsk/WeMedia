package com.lijjsk.user.handler;

import com.alibaba.fastjson.JSON;
import com.lijjsk.model.chat.pojos.Session;
import com.lijjsk.model.chat.pojos.SessionMessage;
import com.lijjsk.user.mapper.SessionMapper;
import com.lijjsk.user.mapper.SessionMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ChatWebSocketHandler implements WebSocketHandler {
    @Autowired
    private SessionMapper sessionMapper;
    @Autowired
    private SessionMessageMapper sessionMessageMapper;
    // 用于存储用户ID和对应的WebSocketSession
    private final Map<Integer, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Autowired
    public ChatWebSocketHandler(SessionMapper sessionMapper, SessionMessageMapper sessionMessageMapper) {
        this.sessionMapper = sessionMapper;
        this.sessionMessageMapper = sessionMessageMapper;
    }

    // 处理连接建立后的逻辑
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("连接建立");
        // 解析 URL 参数
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams();
        Integer sender = Integer.valueOf(queryParams.getFirst("sender"));
        Integer receiver = Integer.valueOf(queryParams.getFirst("receiver"));

        // 根据两个id查询是否有历史会话
        Session sessionBySenderAndReceiver = sessionMapper.getSessionBySenderAndReceiver(Math.min(sender, receiver), Math.max(sender, receiver));

        if (sessionBySenderAndReceiver == null) {
            // 如果没有历史会话，建立一个新会话信息保存到数据库中
            log.info("当前两个用户还没有建立会话，{} and {}", sender, receiver);
            log.info("建立新会话");
            Session newSession = new Session();
            newSession.setSender(Integer.valueOf(sender));
            newSession.setReceiver(Integer.valueOf(receiver));
            newSession.setCreatedTime(new Date());
            sessionMapper.insert(newSession);
        } else {
            // 查询历史记录
            List<SessionMessage> historyMessage = sessionMessageMapper.getHistoryMessageBySessionId(sessionBySenderAndReceiver.getId());
            // 发送历史记录给用户
            for (SessionMessage message : historyMessage) {

               session.sendMessage(new TextMessage(JSON.toJSONString(message)));
            }
            log.info("历史聊天记录发送完成");
        }

        // 用户建立连接时触发此方法
        log.info("用户{}已经上线",sender);
        userSessions.put(sender, session);
    }

    // 处理接收到消息的逻辑
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        // 从 URL 中提取用户的 id，以及用户消息要发送到的用户的 id
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams();
        Integer sender = Integer.valueOf(queryParams.getFirst("sender"));
        Integer receiver = Integer.valueOf(queryParams.getFirst("receiver"));
        //获取当前两个用户的session
        Session thisSession = sessionMapper.getSessionBySenderAndReceiver(Math.min(sender, receiver), Math.max(sender, receiver));
        // 保存 message 到表中
        SessionMessage sessionMessage = new SessionMessage();
        sessionMessage.setSessionId(thisSession.getId());
        sessionMessage.setSender(sender);
        sessionMessage.setReceiver(receiver);
        sessionMessage.setContent(message.getPayload().toString());
        sessionMessage.setCreatedTime(new Date());
        sessionMessageMapper.insert(sessionMessage);

        // 发送消息给要发送到的用户
        sendMessageToUser(receiver, new TextMessage(JSON.toJSONString(sessionMessage)));
    }

    // 处理连接关闭后的逻辑
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 从 URL 中提取当前 WebSocket 连接的用户的 id
        MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(session.getUri()).build().getQueryParams();
        Integer sender = Integer.valueOf(queryParams.getFirst("sender"));

        // 展示提示信息
        log.info("用户{}已经离线", sender);
        // 从当前会话列表中移除当前用户的 WebSocket
        userSessions.remove(sender);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 处理传输错误
        log.info("测试连接错误");
        log.info(exception.getMessage());
    }

    // 向指定用户发送消息
    public void sendMessageToUser(Integer receiver, TextMessage textMessage) {
        WebSocketSession userSession = userSessions.get(receiver);
        if (userSession != null && userSession.isOpen()) {
            try {
                userSession.sendMessage(textMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.error("请求发送到的用户不在线");
        }
    }
}
