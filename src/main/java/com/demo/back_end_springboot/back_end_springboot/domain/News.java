package com.demo.back_end_springboot.back_end_springboot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class News implements Serializable {
    @JsonProperty(value = "Date")
    private String date;
    @JsonProperty(value = "Title")
    private String title;
    @JsonProperty(value = "Url")
    private String url;

    public News() {
    }

    public News(String date, String title, String url) {
        this.date = date;
        this.title = title;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
