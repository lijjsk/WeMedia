package com.lijjsk.test;

import com.lijjsk.authentication.AuthenticationApplication;
import com.lijjsk.authentication.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AuthenticationApplication.class)
public class MyTest {
    @Resource
    UserMapper userMapper;
    @Test
    public void test(){
        System.out.println(userMapper.selectUserByUsername("小白"));
    }
}
