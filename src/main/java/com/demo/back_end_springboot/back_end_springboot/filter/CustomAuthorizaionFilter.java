package com.demo.back_end_springboot.back_end_springboot.filter;

import com.demo.back_end_springboot.back_end_springboot.domain.Jwt;
import com.demo.back_end_springboot.back_end_springboot.domain.Role;
import com.demo.back_end_springboot.back_end_springboot.util.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.demo.back_end_springboot.back_end_springboot.constant.SecurityConstant.PASS_URLS;

public class CustomAuthorizaionFilter extends OncePerRequestFilter {

    private JwtProvider jwtProvider = new JwtProvider();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isUrlCanPass(request)) {
            filterChain.doFilter(request, response);
        } else {
          String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
          if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
              String token = authorizationHeader.substring("Bearer ".length());
              Jwt jwt = jwtProvider.validToken(token);
              if (jwt.isExpire()) {
                  String account = jwt.getAccount();
                  Collection<Role> roles = jwt.getRoles();
                  Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                  roles.forEach(role -> {
                      authorities.add(new SimpleGrantedAuthority(role.getName()));
                  });
                  UsernamePasswordAuthenticationToken authenticationToken =
                          new UsernamePasswordAuthenticationToken(account, null, authorities);
                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                  filterChain.doFilter(request, response);
              } else {
                  response.setHeader("error", jwt.getExceptionMsg());
                  response.setStatus(HttpStatus.FORBIDDEN.value());
                  Map<String, String> errorMsg = new HashMap<>();
                  errorMsg.put("error", jwt.getExceptionMsg());
                  response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                  new ObjectMapper().writeValue(response.getOutputStream(), errorMsg);
              }
          } else {
              filterChain.doFilter(request, response);
          }
        }
    }

    private boolean isUrlCanPass(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return Arrays.asList(PASS_URLS).contains(servletPath);
    }
}
