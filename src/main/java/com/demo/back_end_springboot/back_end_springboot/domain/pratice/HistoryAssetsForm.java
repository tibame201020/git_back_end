package com.demo.back_end_springboot.back_end_springboot.domain.pratice;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HistoryAssetsForm implements Serializable {
    private String account;
    private String startDate;
    private String endDate;

    private DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public HistoryAssetsForm() {
    }

    public HistoryAssetsForm(String account, String startDate, String endDate) {
        this.account = account;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public LocalDate getStartDate() {
        return LocalDate.parse(startDate, DATEFORMATTER);
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return LocalDate.parse(endDate, DATEFORMATTER);
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public DateTimeFormatter getDATEFORMATTER() {
        return DATEFORMATTER;
    }
}
