package com.lijjsk.user.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

//@Component
@Data
@TableName(value = "user")//指定表名
//@ConfigurationProperties(prefix = "user1")
//SpringSecurity会将认证的用户存到UserDetails中
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
    private Integer sumFollowed;
    private Integer sumFollowing;

    //@TableLogic可以设置逻辑删除
    private Integer state;

    //角色信息
    private Set<Identity> identitySet;
    //权限信息
    private Set<String> menus;

}
