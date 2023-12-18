package com.lijjsk.model.wemedia.user.pojos;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@TableName(value = "user")//指定表名
public class User implements Serializable, UserDetails {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "username")
    private String username;
    @TableField(value = "password")
    private String password;
    @TableField(value = "age")
    private Integer age;
    @TableField(value = "sex")
    private Boolean sex;
    @TableField(value = "signature")
    private String signature;
    @TableField(value = "profile_photo")
    private String profilePhoto;
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "email")
    private String email;
    @TableField(value = "sum_followed")
    private Integer sumFollowed;
    @TableField(value = "sum_following")
    private Integer sumFollowing;
    @TableField(value = "state")
    private Integer state;
    //是否私密账号
    @TableField(value = "is_secret")
    private Boolean isSecret;
    //角色信息
    @TableField(exist = false)
    private Set<Identity> identitySet;
    //权限信息
    @TableField(exist = false)
    private Set<String> menus;

    /**
     * SpringSecurity根据getAuthorities获取用户的权限信息
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //权限告知SpringSecurity
        //lambda表达式将Set<String>=》collection<GrantedAuthority>
        if(menus !=null&& !menus.isEmpty()) {
            return menus.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return state==1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return state==1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return state==1;
    }

    @Override
    public boolean isEnabled() {
        return state==1;
    }

}
