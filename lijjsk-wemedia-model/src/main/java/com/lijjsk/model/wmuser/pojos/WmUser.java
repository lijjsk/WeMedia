package com.lijjsk.model.wmuser.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 基本用户信息
 */
@Data
@TableName("wm_user")
public class WmUser {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private Integer id;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 性别 1 男 0 女
     */
    private int sex;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 头像
     */
    private String profile_photo;
}
