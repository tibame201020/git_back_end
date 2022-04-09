package com.demo.back_end_springboot.back_end_springboot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class StockBasicInfo implements Serializable {
    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "date")
    private String date;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "fields")
    private String[] fields;

    @JsonProperty(value = "data")
    private String[][] data;

    @JsonProperty(value = "notes")
    private String[] notes;

    public StockBasicInfo() {
    }

    public StockBasicInfo(String status, String date, String title, String[] fields, String[][] data, String[] notes) {
        this.status = status;
        this.date = date;
        this.title = title;
        this.fields = fields;
        this.data = data;
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    public String[] getNotes() {
        return notes;
    }

    public void setNotes(String[] notes) {
        this.notes = notes;
    }
}
