package com.lijjsk.model.manage.apply.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName(value = "apply")
public class Apply implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 当前用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 审核员id
     */
    @TableField(value = "auditor_id")
    private Integer auditorId;
    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;
    /**
     * 电话号码
     */
    @TableField(value = "phone")
    private String phone;
    /**
     * 申请内容
     */
    @TableField(value = "content")
    private String content;
    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;
}
