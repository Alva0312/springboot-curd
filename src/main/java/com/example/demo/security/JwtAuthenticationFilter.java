package com.example.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthenticationFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(JWTUtils.HEADER_TOKEN_NAME);
        if (Objects.nonNull(token) && token.trim().length() > 0) {
            String payload = JWTUtils.testJwt(token);
            if (Objects.nonNull(payload) && payload.trim().length() > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                UserDetailImpl user = objectMapper.readValue(payload, UserDetailImpl.class);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
