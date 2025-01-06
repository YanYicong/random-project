package com.example.gateway.handler;

import com.example.utils.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    // 不需要 token 验证的路径
    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/random/api/login", "/random/api/register", "/random/api/logout");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        // 如果路径在排除列表中，则直接放行
        if (EXCLUDED_PATHS.contains(path)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst("token");

        if (token != null && !token.isEmpty()) {
            try {
                Claims claims = JwtUtils.extractAllClaims(token);
                // 验证成功，继续处理请求
                exchange.getAttributes().put("user", claims.getSubject());
            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } else {
            // 如果请求中没有 token，则返回 401 状态码
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 设置过滤器的执行顺序，越小越先执行
        return -1;
    }
}