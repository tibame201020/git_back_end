package com.demo.back_end_springboot.back_end_springboot.domain;

import java.io.Serializable;

public class CodeParam implements Serializable {
    private String code;
    private String startDate;
    private String endDate;

    public CodeParam() {
    }

    public CodeParam(String code, String startDate, String endDate) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
