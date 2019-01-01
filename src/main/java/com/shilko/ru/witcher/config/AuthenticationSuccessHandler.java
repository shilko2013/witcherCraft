package com.shilko.ru.witcher.config;

import com.google.gson.Gson;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        httpServletResponse.addCookie(new Cookie("role",authentication.getAuthorities().iterator().next().getAuthority()));
    }
}
