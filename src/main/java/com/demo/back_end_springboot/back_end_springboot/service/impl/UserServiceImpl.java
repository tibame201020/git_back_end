package com.demo.back_end_springboot.back_end_springboot.service.impl;

import com.demo.back_end_springboot.back_end_springboot.domain.OnceToken;
import com.demo.back_end_springboot.back_end_springboot.domain.Role;
import com.demo.back_end_springboot.back_end_springboot.domain.User;
import com.demo.back_end_springboot.back_end_springboot.exception.UserNotFoundException;
import com.demo.back_end_springboot.back_end_springboot.repo.OnceTokenRepo;
import com.demo.back_end_springboot.back_end_springboot.repo.RoleRepo;
import com.demo.back_end_springboot.back_end_springboot.repo.UserRepo;
import com.demo.back_end_springboot.back_end_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private OnceTokenRepo onceTokenRepo;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean isValidUser(User validUser) {
        boolean isValid = false;
        User user = userRepo.findById(validUser.getAccount()).orElseThrow(() -> new UserNotFoundException("user not found"));
        if (user.getPwd().equals(validUser.getPwd())) {
            isValid = true;
        }

        return isValid;
    }

    @Override
    public User addUser(User registerUser) {
        if (registerUser.getAccount().equals("test")) {
            registerUser.setPwd(passwordEncoder.encode(registerUser.getPwd()));
            return userRepo.save(registerUser);
        }

        String rtnMsg;
        //若已有同account
        if (userRepo.findById(registerUser.getAccount()).isPresent()) {
            rtnMsg = String.format("%s已註冊過", registerUser.getAccount());
            registerUser.setMessage(rtnMsg);

            return registerUser;
        } else {
            List<Role> roles = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                roles.add(roleRepo.findById(i).get());
            }
            registerUser.setRoles(roles);
            registerUser.setValid(false);
            registerUser.setPwd(passwordEncoder.encode(registerUser.getPwd()));

            User user = userRepo.save(registerUser);
            rtnMsg = String.format("%s已註冊成功", user.getAccount());
            user.setMessage(rtnMsg);

            return user;
        }
    }

    @Override
    public User updatePwd(User user) {
        //驗證帳號
        String rtnMsg;
        if (!userRepo.findById(user.getAccount()).isPresent()) {
            rtnMsg = "帳號不存在";
        } else if (!userRepo.findById(user.getAccount()).get().getPwd().equals(user.getPwd())) {
            rtnMsg = "原有密碼不對";
        } else {
            user.setPwd(user.getChangePwd());
            user.setPwd(passwordEncoder.encode(user.getPwd()));
            user.setChangePwd("");
            user = userRepo.save(user);
            rtnMsg = String.format("%s帳號已更新密碼，往後請用新密碼登入", user.getAccount());
        }
        user.setMessage(rtnMsg);

        return user;
    }

    @Override
    public boolean isAlreadyHaveAccount(String account) {
        Optional<User> optional = userRepo.findById(account);
        return optional.isPresent();
    }

    @Override
    public boolean isAlreadyHaveMail(String mail) {
        Optional<User> optional = userRepo.findFirstByMail(mail);
        return optional.isPresent();
    }

    @Override
    public boolean isAlreadyHavePhone(String phone) {
        Optional<User> optional = userRepo.findFirstByPhone(phone);
        return optional.isPresent();
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepo.findAll();
    }

    @Override
    public void addRole(Role role) {
        roleRepo.save(role);
    }

    @Override
    public User getUser(String account) {
        Optional<User> optional = userRepo.findById(account);
        return optional.get();
    }

    @Override
    public User enableUser(String account) {
        User user = new User();
        if (!userRepo.findById(account).isPresent()) {
            user.setMessage("the account does not exist");
            return user;
        }

        user = userRepo.getById(account);
        if (user.isValid()) {
            user.setMessage("the account is already enable");
        } else {
            user.setValid(true);
            user = userRepo.save(user);
            user.setMessage("the account is enable now");
        }
        return user;
    }

    @Override
    public Map<String, Object> checkAccountAndMail(User user) {
        Map<String, Object> rtnMap = new HashMap<>();
        boolean checkResult;
        User userGetByAccount = getUser(user.getAccount());
        checkResult = user.getMail().equals(userGetByAccount.getMail());
        rtnMap.put("checkResult", checkResult);
        rtnMap.put("user_info", user);
        return rtnMap;
    }

    @Override
    public boolean checkResetToken(String account, String token) {
        Optional<OnceToken> optional = onceTokenRepo.findByAccountAndToken(account, token);
        if (optional.isPresent()) {
            onceTokenRepo.delete(optional.get());
            return true;
        } else {
            return false;
        }
    }
}
