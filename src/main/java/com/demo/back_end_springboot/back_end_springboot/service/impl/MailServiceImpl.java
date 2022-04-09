package com.demo.back_end_springboot.back_end_springboot.service.impl;

import com.demo.back_end_springboot.back_end_springboot.domain.Auth;
import com.demo.back_end_springboot.back_end_springboot.domain.OnceToken;
import com.demo.back_end_springboot.back_end_springboot.domain.User;
import com.demo.back_end_springboot.back_end_springboot.repo.OnceTokenRepo;
import com.demo.back_end_springboot.back_end_springboot.repo.UserRepo;
import com.demo.back_end_springboot.back_end_springboot.service.MailService;
import com.demo.back_end_springboot.back_end_springboot.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private OnceTokenRepo onceTokenRepo;
    @Autowired
    private UserRepo userRepo;

    private final String FRONT_BASE_URL = "https://tibame201020.github.io/front_end_angular/";

    @Override
    public User sendValidMail(User user) {
        SimpleMailMessage message = getSimpleMailMessage(user);
        message.setSubject("HI " + user.getAccount() + " Thanks Register Start Ur Journey");
        String valid_token = jwtProvider.getToken(new Auth(user), 7 * 24 * 60 * 60 * 1000, "");
        //this is need to generate a link to valid user
        String preStr = "Dear " + user.getAccount() + " :" + "\n";

        preStr = preStr + "plz click the under url to enable ur account" + "\n";

        String base_enable_url = FRONT_BASE_URL + "valid?validToken=";
        message.setText(preStr + base_enable_url + valid_token);
        sendMail(message);
        return user;
    }

    @Override
    public User sendResetPwdMail(User user) {
        SimpleMailMessage message = getSimpleMailMessage(user);
        String reset_token = jwtProvider.getToken(new Auth(user), 10 * 60 * 1000, user.getMail());
        message.setSubject("Reset Ur Password");
        String preStr = "Dear " + user.getAccount() + " :" + "\n";
        preStr = preStr + "plz click the under url to reset ur pwd, but it's only have ten min to reset" + "\n";
        String base_enable_url = FRONT_BASE_URL + "user/reset_pwd?resetToken=";
        message.setText(preStr + base_enable_url + reset_token);
        sendMail(message);
        onceTokenRepo.save(new OnceToken(user.getMail(), reset_token));
        return user;
    }

    @Override
    public Map<String, Object> sendMailForLogin(String mail) {
        Map<String, Object> rtnMap = new HashMap<>();
        Optional<User> optionalUser = userRepo.findFirstByMail(mail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            SimpleMailMessage message = getSimpleMailMessage(user);
            String login_token = jwtProvider.getToken(new Auth(user), 10 * 60 * 1000, "");
            message.setSubject("This is for ur once login code");
            String preStr = "Dear " + user.getAccount() + " :" + "\n";
            preStr = preStr + "this is the code to use login, but it's only have ten min valid" + "\n";
            String random = JwtProvider.getRandomInts();
            message.setText(preStr + random);
            sendMail(message);
            onceTokenRepo.save(new OnceToken(user.getMail(), login_token, random));
            rtnMap.put("result", true);
            user.setPwd(null);
            rtnMap.put("user_info", user);
        } else {
            rtnMap.put("result", false);
        }
        return rtnMap;
    }

    private SimpleMailMessage getSimpleMailMessage(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getMail());
        message.setFrom("backservice0408@gmail.com");
        return message;
    }

    private void sendMail(SimpleMailMessage message) {
        new Thread(() -> mailSender.send(message)).start();
    }
}
