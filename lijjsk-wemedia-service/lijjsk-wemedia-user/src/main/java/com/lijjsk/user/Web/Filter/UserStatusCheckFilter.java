package com.lijjsk.user.Web.Filter;

import com.lijjsk.model.wemedia.user.enums.UserStates;
import com.lijjsk.utils.common.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class UserStatusCheckFilter extends OncePerRequestFilter {
    //需要检查的路径
    private static final List<String> PROTECTED_PATHS = Arrays.asList(
            "/api/protected1",
            "/api/protected2"
            // ... 其他需要保护的路径
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取用户的身份信息
        //获取token
        String token = request.getHeader("Authorization");
        //没有token可能是login，直接方向（此后由其他过滤器处理）
        if (token == null) {
            doFilter(request, response, filterChain);
            return;
        }
        //有token,Jwt解析数据
        log.info("UserStatusCheckFilter：token=============>{}", token);
        Claims claims = null;
        try {
            //解析token
            claims = JwtUtils.parseToken(token);
        } catch (SignatureException e) {
            //验签出错会导致乱码，设置格式
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("验签失败，请重新登录！！！！");
            return;
        }
        // 检查用户状态
        Integer states = claims.get("用户状态", Integer.class);
        //用户状态，使用枚举类
        for (String protectedPaths : PROTECTED_PATHS) {
            if (protectedPaths.equals(request.getRequestURI())) {
                if (UserStates.valueOf(states) == UserStates.MUTED) {
                    response.getWriter().write("用户已经被禁言");
                    return;
                }
                if (UserStates.valueOf(states) == UserStates.BANNED) {
                    response.getWriter().write("用户已经被封禁");
                    return;
                }
            }
        }

        // 如果用户被封禁或禁言，返回错误响应
        // 否则继续过滤器链
        //放行
        doFilter(request,response,filterChain);
    }
}
