package com.demo.back_end_springboot.back_end_springboot.domain.pratice;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockVolume implements Serializable {
    private String code;
    private BigDecimal volume;

    public StockVolume() {
    }


    public StockVolume(String code, BigDecimal volume) {
        this.code = code;
        this.volume = volume;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
