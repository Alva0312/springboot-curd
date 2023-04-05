package com.example.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessHandle implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ObjectMapper objectMapper = new ObjectMapper();

        if (auth.getPrincipal() instanceof UserDetailImpl) {
            UserDetailImpl principal = (UserDetailImpl) auth.getPrincipal();
            JWT jwt = null;
            try {
                jwt = new JWT(objectMapper.writeValueAsString(principal));
            } catch (Exception e) {
                e.printStackTrace();
            }
            httpServletResponse.getWriter().write("{\"code\": \"200\", \"msg\": \"Login successful\"}"
                    + "\nusername: " + ((UserDetailImpl) auth.getPrincipal()).getUsername()
                    + "\ntoken: " + jwt
            );
        }
    }
}
