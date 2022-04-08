package com.demo.back_end_springboot.back_end_springboot.domain.pratice;

public class PracticeForm {
    private String account;
    private String code;
    private String price;
    private String volume;
    private String behavior;

    public PracticeForm() {
    }

    public PracticeForm(String account, String code, String price, String volume, String behavior) {
        this.account = account;
        this.code = code;
        this.price = price;
        this.volume = volume;
        this.behavior = behavior;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        if (code.contains("-")) {
            code = code.split("-")[0].trim();
        }
        return code.trim();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }
}
