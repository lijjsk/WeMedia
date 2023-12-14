package com.lijjsk.authentication.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.authentication.mapper.MenuMapper;
import com.lijjsk.authentication.mapper.UserMapper;
import com.lijjsk.authentication.pojo.Identity;
import com.lijjsk.authentication.pojo.Menu;
import com.lijjsk.authentication.pojo.User;
import com.lijjsk.authentication.service.IUserService;
import com.lijjsk.authentication.utils.JwtUtils;
import com.lijjsk.model.wemedia.user.dtos.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Resource
    MenuMapper menuMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtUtils jwtUtils;

    /**
     * 登录，查询权限
     */
    @Override
    public Map<String,Object> login(UserRequestDto userRequestDto) {

        //传入用户名，密码
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userRequestDto.getUsername(), userRequestDto.getPassword());
        //实现登录逻辑,会去调用在UserDetails里定义的loadUserByUsername
        //authenticate就是UserDetails
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(authentication);
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.info("用户名或者密码错误!");
            return null;
        }
        //获取返回的用户
        User user = (User) authenticate.getPrincipal();
        log.info("登录后的用户=======》{}", user);
        if (user == null) {
            return null;
        }
        //根据用户信息生成token
        Map<String, Object> tokenmap = new HashMap<>();
        tokenmap.put("用户Id", user.getId());
        tokenmap.put("用户Name", user.getUsername());
        tokenmap.put("用户身份", user.getIdentitySet());
        tokenmap.put("用户权限", user.getMenus());
        tokenmap.put("用户状态", user.getState());

        //存储用户信息的map
        Map<String,Object> userMap=new HashMap<>();
        log.info("用户map==============================={}",tokenmap);
        //获取并存储用户个人信息
        userMap.put("userInfo",user);
        //存储token
        userMap.put("token",jwtUtils.creatToken(tokenmap));
        return userMap;
    }

    /**
     * 根据token在其他微服务发送请求时，判断是否有权限
     */
    @Override
    public Boolean authority_judge(String token, String uri) {

        if ("/auth/login".equals(uri) || "/logout".equals(uri) || "/error".equals(uri)) {
            //通过
            return true;
        }
        //根据URI获取路径的访问权限
        Menu menu = menuMapper.selectMenuByURI(uri);
        if (menu == null) {
            //直接不通过,因为路径错误
            return false;
        }
        //拿到对应权限
        String perms = menu.getPerms();
        log.info("路径需要的权限=======================>{}", perms);
        if (perms == null || perms.trim().equals("")) {
            //直接通过,因为此接口路径不需要权限
            return true;
        }
        //与用户权限进行判断
        //有token,Jwt解析数据
        log.info("token=============>{}", token);
        Claims claims = null;
        try {
            claims = jwtUtils.parseToken(token);
        } catch (SignatureException e) {
            //验签出错会导致乱码，设置格式
            return false;
        }

        //获取到信息
        Integer id = claims.get("用户Id", Integer.class);
        String name = claims.get("用户Name", String.class);
        ArrayList<Identity> identitys = claims.get("用户身份", ArrayList.class);
        ArrayList<String> menus = claims.get("用户权限", ArrayList.class);

        Set<String> menuSet = new HashSet<>();
        menuSet.addAll(menus);
        for (String per_menu : menuSet) {
            if (per_menu.equals(perms)) {
                return true;
            }
        }
        return false;
    }
}
