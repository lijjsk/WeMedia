package com.lijjsk.wemedia.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.model.common.dtos.UserRequestDto;
import com.lijjsk.wemedia.login.mapper.UserMapper;
import com.lijjsk.wemedia.login.pojo.User;
import com.lijjsk.wemedia.login.service.IUserService;
import com.lijjsk.wemedia.login.utils.JwtUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    UserMapper userMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtUtils jwtUtils;
    public User getByNameAndPassword( String name,String password){
        return userMapper.getByNameAndPassword(name,password);
    }

    @Override
    public String login(UserRequestDto userRequestDto) {
        //传入用户名，密码
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userRequestDto.getUsername(), userRequestDto.getPassword());
        //实现登录逻辑,会去调用在UserDetails里定义的loadUserByUsername
        //authenticate就是UserDetails
        Authentication authenticate = null;
        try {
            authenticate=authenticationManager.authenticate(authentication);
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.info("用户名或者密码错误!");
            return "用户名或者密码错误!";
        }
        //获取返回的用户
        User user = (User) authenticate.getPrincipal();
        log.info("登录后的用户=======》{}", user);
        if(user==null){
            return "用户名或者密码错误!";
        }
        //根据用户信息生成token
        Map<String,Object> map=new HashMap<>();
        map.put("用户Id",user.getId());
        map.put("用户Name",user.getUsername());
        map.put("用户身份",user.getIdentitySet());
        map.put("用户权限",user.getMenus());
        return jwtUtils.creatToken(map);
    }

}
