package com.demo.back_end_springboot.back_end_springboot.domain;

import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class Auth implements UserDetails {

    private User user;

    public Auth(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
        this.user.getRoles().forEach(role -> roles.add(new SimpleGrantedAuthority(role.getName())));
        return roles;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return this.user.getPwd();
    }

    @Override
    public String getUsername() {
        return this.user.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isValid();
    }
}
