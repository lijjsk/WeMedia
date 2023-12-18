package com.lijjsk.schedule.config;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //关闭csrf
        httpSecurity.csrf(csrf -> csrf.disable());

        httpSecurity.authorizeHttpRequests(
                authorizeHttpRequests -> authorizeHttpRequests
                        //使用自定义认证逻辑
                        .anyRequest().permitAll()
        );
        //这里只需要认证token就好
        return httpSecurity.build();
    }

}
