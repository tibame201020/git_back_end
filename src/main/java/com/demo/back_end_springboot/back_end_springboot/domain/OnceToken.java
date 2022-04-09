package com.demo.back_end_springboot.back_end_springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class OnceToken implements Serializable {
    @Id
    @Column(nullable = false)
    private String account;
    @Column(nullable = false)
    private String token;
    @Column(nullable = true)
    private String shortRandom;

    public OnceToken() {
    }

    public OnceToken(String account, String token) {
        this.account = account;
        this.token = token;
    }

    public OnceToken(String account, String token, String shortRandom) {
        this.account = account;
        this.token = token;
        this.shortRandom = shortRandom;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getShortRandom() {
        return shortRandom;
    }

    public void setShortRandom(String shortRandom) {
        this.shortRandom = shortRandom;
    }
}
