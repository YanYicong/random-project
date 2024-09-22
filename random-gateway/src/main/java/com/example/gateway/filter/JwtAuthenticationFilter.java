package com.example.gateway.filter;

import com.example.utils.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * token过滤器
 * 拦截并解析token，验证有效性
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//    不需要 token 验证的路径
    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/random/api/login", "/random/api/register","/random/api/logout");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // 如果路径在排除列表中，则直接放行
        if (EXCLUDED_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("token");

        if (token != null && !token.isEmpty()) {
            try {
                Claims claims = JwtUtils.extractAllClaims(token);
                // 验证成功，继续处理请求
                request.setAttribute("user", claims.getSubject());
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        } else {
            // 如果请求中没有 token，则返回 401 状态码
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
