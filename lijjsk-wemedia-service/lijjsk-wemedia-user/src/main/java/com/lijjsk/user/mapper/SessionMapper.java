package com.lijjsk.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.model.chat.pojos.Session;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SessionMapper extends BaseMapper<Session> {
    Session getSessionBySenderAndReceiver(@Param("sender") Integer sender, @Param("receiver") Integer receiver);
}
