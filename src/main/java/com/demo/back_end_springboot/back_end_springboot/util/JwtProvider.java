package com.demo.back_end_springboot.back_end_springboot.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.back_end_springboot.back_end_springboot.domain.Auth;
import com.demo.back_end_springboot.back_end_springboot.domain.Jwt;
import com.demo.back_end_springboot.back_end_springboot.domain.Role;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

import static com.demo.back_end_springboot.back_end_springboot.constant.SecurityConstant.SECRET;
import static com.demo.back_end_springboot.back_end_springboot.constant.SecurityConstant.TOKEN_CAN_NOT_VERIFY;
import static com.demo.back_end_springboot.back_end_springboot.constant.SecurityConstant.VALID_SUCCESSFUL_MSG;
import static com.demo.back_end_springboot.back_end_springboot.constant.SecurityConstant.VALID_UNSUCCESSFUL_EXPIRED_MSG;
import static com.demo.back_end_springboot.back_end_springboot.constant.SecurityConstant.VALID_UNSUCCESSFUL_UN_VALID_MSG;

@Service
public class JwtProvider {
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtProvider() {
        this.algorithm = Algorithm.HMAC512(SECRET.getBytes());
        this.verifier = JWT.require(algorithm).build();
    }

    public String getToken(Auth auth, long expiresTime, String issue) {
        String token = JWT.create()
                .withSubject(auth.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresTime))
                .withIssuer(issue)
                .withClaim("role", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        return token;
    }

    public Jwt validToken (String token) {
        Jwt jwt = new Jwt();
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            jwt.setAccount(decodedJWT.getSubject());
            jwt.setIssue(decodedJWT.getIssuer());
            jwt.setRoles(decodedJWT.getClaim("role").asList(Role.class));
            jwt.setMessage(VALID_SUCCESSFUL_MSG);
            jwt.setExpire(true);
        } catch (TokenExpiredException tokenExpiredException) {
            // the token is Expired
            jwt.setMessage(VALID_UNSUCCESSFUL_EXPIRED_MSG);
            jwt.setExceptionMsg(tokenExpiredException.getMessage());
            jwt.setExpire(false);
        } catch (JWTDecodeException jwtDecodeException) {
            // the token is un-valid
            jwt.setMessage(VALID_UNSUCCESSFUL_UN_VALID_MSG);
            jwt.setExceptionMsg(jwtDecodeException.getMessage());
            jwt.setExpire(false);
        } catch (Exception e) {
            jwt.setMessage(TOKEN_CAN_NOT_VERIFY);
            jwt.setExceptionMsg(e.getMessage());
            jwt.setExpire(false);
        }
        return jwt;
    }

    public static String getRandomInts() {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        String rtn = "";
        for (int i = 0; i < 5; i++) {
            int random = randomDataGenerator.nextInt(0, 9);
            rtn = rtn + random;
        }
        return rtn;
    }
}
