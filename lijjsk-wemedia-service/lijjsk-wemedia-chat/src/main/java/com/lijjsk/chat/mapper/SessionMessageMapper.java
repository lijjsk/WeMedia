package com.lijjsk.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.model.chat.pojos.SessionMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SessionMessageMapper extends BaseMapper<SessionMessage> {
    List<SessionMessage> getHistoryMessageBySessionId(Integer sessionId);
}
