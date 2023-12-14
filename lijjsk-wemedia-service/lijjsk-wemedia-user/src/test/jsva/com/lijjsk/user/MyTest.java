package com.lijjsk.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lijjsk.user.mapper.UserMapper;
import com.lijjsk.user.pojo.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class MyTest {
    @Resource
    PasswordEncoder encoder;
    @Resource
    UserMapper userMapper;
    //select
    @Test
    public void test44(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();

        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<User>();

        //设置条件
        queryWrapper.select("signature");
        queryWrapper.between("sum_followed",30,50);
        //
        lambdaQueryWrapper.select(User::getSignature).between(User::getSumFollowed,30, 50);
        List<User> list1=userMapper.selectList(queryWrapper);
        List<User> list2=userMapper.selectList(lambdaQueryWrapper);
        System.out.println(list1);
        System.out.println(list2);
    }
    @Test
    public void updateTest(){
        LambdaUpdateWrapper<User> updateWrapper=new LambdaUpdateWrapper<User>();
        Integer userId=530984962;
        //添加筛选条件
        updateWrapper.eq( User::getId,userId).and(u->u.eq(User::getAge,65));
        updateWrapper.set(User::getUsername,"方太").set(User::getPassword,encoder.encode("handsome@123"));

        int result=userMapper.update(updateWrapper);
        System.out.println(result);
    }
}
