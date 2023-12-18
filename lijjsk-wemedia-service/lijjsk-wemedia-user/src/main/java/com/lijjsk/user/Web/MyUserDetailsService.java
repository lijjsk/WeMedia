package com.lijjsk.user.Web;

import com.lijjsk.model.wemedia.user.pojos.Identity;
import com.lijjsk.model.wemedia.user.pojos.Menu;
import com.lijjsk.model.wemedia.user.pojos.User;
import com.lijjsk.user.mapper.IdentityMapper;
import com.lijjsk.user.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义loadUserByUsername，配置SpringSecurity查询数据库以获取用户信息
 */
@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
    /**
     * 根据用户命查询用户
     * 返回UserDetails，SpringSecurity定义的，用于存储用户信息
     * MyUserDetailsService就是UserDetailsService（MyUserDetailsService implements UserDetailsService）
     *
     * @throws UsernameNotFoundException
     */
    @Resource
    private UserMapper userMapper;
    @Resource
    private IdentityMapper identityMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername========>,要登陆的用户用户名：{}", username);

        //根据用户名获取用户
        User user=userMapper.selectUserByUsername(username);
        log.info("登录的用户========》{}",user);

        //查询用户的权限信息
        if(user!=null){
            Set<Identity> identitySet =user.getIdentitySet();
            //存储身份信息，批量查询而不是重复查询数据库
            Set<Integer> identityIds= new HashSet<>(identitySet.size());
            for(Identity identity:identitySet){
                identityIds.add(identity.getId());
            }
            log.info("登录的用户身份============》{}",identityIds);
            //将身份id集合用于查询权限
            Set<Menu> menus=identityMapper.selectMenuByIdentityIdS(identityIds);
            log.info("登录的用户的权限================》{}",menus);
            Set<String> strings=new HashSet<>(menus.size());
            for(Menu menu:menus){
                strings.add(menu.getPerms());
            }
            //存入user的Menus中
            user.setMenus(strings);
        }

        return user;
    }
}















