package com.lijjsk.user.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName(value = "menu")
public class Menu implements Serializable {
    @TableId(value = "id")
    private Integer id;
    private String name;
    private String parentId;
    private String perms;
    private String menuType;
    private String path;
}
