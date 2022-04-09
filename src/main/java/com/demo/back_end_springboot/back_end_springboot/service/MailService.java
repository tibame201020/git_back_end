package com.demo.back_end_springboot.back_end_springboot.service;

import com.demo.back_end_springboot.back_end_springboot.domain.User;

import java.util.Map;

public interface MailService {
    User sendValidMail(User user);

    User sendResetPwdMail(User user);

    Map<String, Object> sendMailForLogin(String mail);
}
