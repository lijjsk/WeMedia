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
@TableName("session")
public class Session implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 会话id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 发送者id
     */
    @TableField(value = "sender")
    private Integer sender;
    /**
     * 接收者id
     */
    @TableField(value = "receiver")
    private Integer receiver;
    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;
    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;
}
