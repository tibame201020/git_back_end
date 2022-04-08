package com.demo.back_end_springboot.back_end_springboot.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class StockData implements Serializable {
    @EmbeddedId
    private StockDataPk stockDataPk;
    private String tradeVolume;
    private String tradeValue;
    private String openingPrice;
    private String highestPrice;
    private String lowestPrice;
    private String closingPrice;
    @Column(name="change2")
    private String change;
    @Column(name="transaction2")
    private String transaction;
    private String yearMonthCode;
    private String yearMonthDate;
    private String codeOut;

    public StockData() {}


    public StockDataPk getStockDataPk() {
        return stockDataPk;
    }

    public void setStockDataPk(StockDataPk stockDataPk) {
        this.stockDataPk = stockDataPk;
    }

    public String getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(String tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public String getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(String tradeValue) {
        this.tradeValue = tradeValue;
    }

    public String getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(String openingPrice) {
        this.openingPrice = openingPrice;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(String closingPrice) {
        this.closingPrice = closingPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getYearMonthDate() {
        return yearMonthDate;
    }

    public void setYearMonthDate(String yearMonthDate) {
        this.yearMonthDate = yearMonthDate;
    }

    public String getCodeOut() {
        return codeOut;
    }

    public void setCodeOut(String codeOut) {
        this.codeOut = codeOut;
    }

    public String getYearMonthCode() {
        return yearMonthCode;
    }

    public void setYearMonthCode(String yearMonthCode) {
        this.yearMonthCode = yearMonthCode;
    }

    @Embeddable
    public static class StockDataPk implements Serializable {
        private String code;
        private String date;

        public StockDataPk() {}
        public StockDataPk(String code, String date) {
            this.code = code;
            this.date = date;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
