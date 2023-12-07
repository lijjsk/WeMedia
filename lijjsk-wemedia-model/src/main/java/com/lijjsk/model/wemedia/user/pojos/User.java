package com.lijjsk.model.wemedia.user.pojos;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

//@Component
@Data
@TableName(value = "user")//指定表名
public class User implements Serializable{
    @TableId(value = "id")
    private Integer id;
    private String username;
    private String password;
    //在这里区别就是，int在没有赋值时为0，而Integer没有赋值可以为空，
    //因为int是原始数据类型，处理不了null,,并且在赋值  age: null，会报错
    private Integer age;
    private Boolean sex;
    private String signature;
    private String profilePhoto;
    private String phone;
    private String email;


}
