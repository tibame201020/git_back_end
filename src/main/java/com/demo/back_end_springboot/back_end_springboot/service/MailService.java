package com.demo.back_end_springboot.back_end_springboot.service;

import com.demo.back_end_springboot.back_end_springboot.domain.User;

import java.util.HashMap;
import java.util.Map;

public interface MailService {
    public User sendValidMail(User user);
    public User sendResetPwdMail(User user);

    Map<String, Object> sendMailForLogin(String mail);
}
