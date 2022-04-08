package com.demo.back_end_springboot.back_end_springboot.filter;

import com.demo.back_end_springboot.back_end_springboot.domain.Auth;
import com.demo.back_end_springboot.back_end_springboot.domain.User;
import com.demo.back_end_springboot.back_end_springboot.util.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.demo.back_end_springboot.back_end_springboot.constant.SecurityConstant.USER_LOGIN_NOT_ALLOW;
import static com.demo.back_end_springboot.back_end_springboot.constant.SecurityConstant.USER_LOGIN_NOT_ENABLED;

public class AuthorFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;


    public AuthorFilter (AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       String account = null;
       String pwd = null;

       if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
           try {
               Map<String, String> map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
               account = map.get("account");
               pwd = map.get("pwd");
           } catch (Exception e) {
               e.printStackTrace();
           }
       } else {
           account = request.getParameter("username");
           pwd = request.getParameter("password");
       }

       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, pwd);
       return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //for valid success
        //generate json web token here
        Auth auth = (Auth) authResult.getPrincipal();

        String access_token = jwtProvider.getToken(auth,  1000 * 60 * 60 * 12, request.getRequestURL().toString());
        String refresh_token = jwtProvider.getToken(auth, 1000 * 60 * 60 * 24, request.getRequestURL().toString());

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        tokens.put("user_info", jwtProvider.validToken(access_token));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> unSuccessMsgMap = new HashMap<>();
        String unSuccessMsg;
        if (failed.getMessage().contains("disabled")) {
            unSuccessMsg = USER_LOGIN_NOT_ENABLED;
        } else {
            unSuccessMsg = USER_LOGIN_NOT_ALLOW;
        }
        unSuccessMsgMap.put("un_success_msg", unSuccessMsg);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), unSuccessMsgMap);
    }
}
