package com.lijjsk.social.config;

import com.lijjsk.social.web.filter.JwtAuthenticationFilter;
import com.lijjsk.social.web.manager.SttAuthorizationManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@Slf4j
/**
 *
 * 开启SpringSecurity 后默认注册大量过滤器
 * 过滤器链，责任链模式
 */
public class WebSecurityConfig {
    //jwt过滤器
    @Resource
    JwtAuthenticationFilter jwtAuthenticationFilter;

    //配置自定义的权限比对
    @Resource
    SttAuthorizationManager sttAuthorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //关闭csrf
        httpSecurity.csrf(csrf -> csrf.disable());

        httpSecurity.authorizeHttpRequests(
                authorizeHttpRequests -> authorizeHttpRequests
                        //使用自定义认证逻辑
                        .anyRequest().access(sttAuthorizationManager)
//                        .anyRequest().permitAll()
        );
        //将jwt过滤器添加到过滤器链中
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //这里只需要认证token就好
        return httpSecurity.build();
    }

}
