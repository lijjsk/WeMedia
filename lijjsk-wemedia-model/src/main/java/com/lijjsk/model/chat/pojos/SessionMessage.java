package com.lijjsk.model.chat.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("session_message")
public class SessionMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 消息id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 会话id
     */
    @TableField(value = "session_id")
    private Integer sessionId;
    /**
     * 消息发送者
     */
    @TableField(value = "sender")
    private Integer sender;
    /**
     * 消息接收者
     */
    @TableField(value = "receiver")
    private Integer receiver;
    /**
     * 消息创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;
    /**
     * 消息内容
     */
    @TableField(value = "content")
    private String content;
    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;
}
