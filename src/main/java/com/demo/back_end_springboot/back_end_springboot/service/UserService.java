package com.demo.back_end_springboot.back_end_springboot.service;

import com.demo.back_end_springboot.back_end_springboot.domain.Role;
import com.demo.back_end_springboot.back_end_springboot.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    boolean isValidUser(User user);

    User addUser(User registerUser);

    User updatePwd(User user);

    boolean isAlreadyHaveAccount(String account);

    boolean isAlreadyHaveMail(String mail);

    boolean isAlreadyHavePhone(String phone);

    List<Role> getAllRole();

    void addRole(Role role);

    User getUser(String account);

    User enableUser(String account);

    Map<String, Object> checkAccountAndMail(User user);

    boolean checkResetToken(String account, String token);
}
