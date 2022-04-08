package com.demo.back_end_springboot.back_end_springboot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CompanyInfo implements Serializable {
    @JsonProperty(value="上市日期")
    private String publishDate;
    @JsonProperty(value="公司代號")
    private String code;
    @JsonProperty(value="公司名稱")
    private String name;
    @JsonProperty(value="總經理")
    private String manager;
    @JsonProperty(value="董事長")
    private String chairman;
    @JsonProperty(value="住址")
    private String address;
    @JsonProperty(value="實收資本額")
    private String paidInCapital;
    @JsonProperty(value="網址")
    private String url;

    public CompanyInfo() {}

    public CompanyInfo(String publishDate, String code, String name, String manager, String chairman, String address, String paidInCapital, String url) {
        this.publishDate = publishDate;
        this.code = code;
        this.name = name;
        this.manager = manager;
        this.chairman = chairman;
        this.address = address;
        this.paidInCapital = paidInCapital;
        this.url = url;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getChairman() {
        return chairman;
    }

    public void setChairman(String chairman) {
        this.chairman = chairman;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaidInCapital() {
        return paidInCapital;
    }

    public void setPaidInCapital(String paidInCapital) {
        this.paidInCapital = paidInCapital;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
