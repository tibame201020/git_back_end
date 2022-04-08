package com.demo.back_end_springboot.back_end_springboot.domain.pratice;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class StockRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String account;
    private String stockCode;
    private BigDecimal volume;
    private BigDecimal unitPrice;
    private BigDecimal beforeCash;
    private BigDecimal remainCash;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp recordTime;

    public StockRecord() {
    }

    public StockRecord(String account, String stockCode, BigDecimal volume, BigDecimal unitPrice, BigDecimal beforeCash, BigDecimal remainCash) {
        this.account = account;
        this.stockCode = stockCode;
        this.volume = volume;
        this.unitPrice = unitPrice;
        this.beforeCash = beforeCash;
        this.remainCash = remainCash;
    }

    public StockRecord(Integer id, String account, String stockCode, BigDecimal volume, BigDecimal unitPrice, BigDecimal beforeCash, BigDecimal remainCash, Timestamp recordTime) {
        this.id = id;
        this.account = account;
        this.stockCode = stockCode;
        this.volume = volume;
        this.unitPrice = unitPrice;
        this.beforeCash = beforeCash;
        this.remainCash = remainCash;
        this.recordTime = recordTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getBeforeCash() {
        return beforeCash;
    }

    public void setBeforeCash(BigDecimal beforeCash) {
        this.beforeCash = beforeCash;
    }

    public BigDecimal getRemainCash() {
        return remainCash;
    }

    public void setRemainCash(BigDecimal remainCash) {
        this.remainCash = remainCash;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public String toString() {
        return "StockRecord{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", stockCode='" + stockCode + '\'' +
                ", volume=" + volume +
                ", unitPrice=" + unitPrice +
                ", beforeCash=" + beforeCash +
                ", remainCash=" + remainCash +
                ", recordTime=" + recordTime +
                '}';
    }
}
