package com.demo.back_end_springboot.back_end_springboot.service.impl;

import com.demo.back_end_springboot.back_end_springboot.domain.Auth;
import com.demo.back_end_springboot.back_end_springboot.domain.Jwt;
import com.demo.back_end_springboot.back_end_springboot.domain.OnceToken;
import com.demo.back_end_springboot.back_end_springboot.domain.User;
import com.demo.back_end_springboot.back_end_springboot.repo.OnceTokenRepo;
import com.demo.back_end_springboot.back_end_springboot.repo.UserRepo;
import com.demo.back_end_springboot.back_end_springboot.service.AuthService;
import com.demo.back_end_springboot.back_end_springboot.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OnceTokenRepo onceTokenRepo;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new Auth(user);
    }

    @Override
    public Map<String, Object> checkShortCode(OnceToken onceToken) {
        Map<String, Object> rtnMap = new HashMap<>();
        int rtnStatusCode = 0;
        String rtnMsg = "驗證碼錯誤，請確認是否正確輸入";

        Optional<OnceToken> optionalOnceToken =
                onceTokenRepo.findByAccountAndShortRandom(onceToken.getAccount(), onceToken.getShortRandom());
        if (optionalOnceToken.isPresent()) {
            onceTokenRepo.delete(optionalOnceToken.get());
            Jwt jwt = jwtProvider.validToken(optionalOnceToken.get().getToken());
            if (jwt.isExpire()) {

                String access_token = jwtProvider.getToken(
                        new Auth(userRepo.getById(jwt.getAccount())), 60 * 60 * 1000, "");
                String refresh_token = jwtProvider.getToken(
                        new Auth(userRepo.getById(jwt.getAccount())), 30 * 1000, "");
                rtnStatusCode = 1;
                rtnMsg = "已成功驗證";
                rtnMap.put("user_info", jwt);
                rtnMap.put("access_token", access_token);
                rtnMap.put("refresh_token", refresh_token);
            } else {
                rtnStatusCode = 2;
                rtnMsg = "驗證碼已過期，請重新發送驗證碼";
            }
        }
        rtnMap.put("rtnStatusCode", rtnStatusCode);
        rtnMap.put("rtnMsg", rtnMsg);

        return rtnMap;
    }
}
