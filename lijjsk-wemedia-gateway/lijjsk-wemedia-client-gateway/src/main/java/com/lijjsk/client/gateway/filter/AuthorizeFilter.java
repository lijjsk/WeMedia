package com.lijjsk.client.gateway.filter;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.lijjsk.client.gateway.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizeFilter implements Ordered, GlobalFilter {
    @Resource
    JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //2.判断是否是登录
        if(request.getURI().getPath().contains("/authenticate/login") || request.getURI().getPath().contains("/user/register") || request.getURI().getPath().contains("/chat")){
            //放行
            return chain.filter(exchange);
        }
        //3.获取token

        String token = request.getHeaders().getFirst("Authorization");

        //4.判断token是否存在
        if(StringUtils.isBlank(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();

        }
        //5.判断token是否有效
        //有token,Jwt解析数据
        log.info("token=============>{}",token);
        Claims claims=null;
        try{
            //解析token,查看token是否过期
            claims = jwtUtils.parseToken(token);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //6.放行
        return chain.filter(exchange);
    }

    /**
     * 优先级设置 值越小 优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
