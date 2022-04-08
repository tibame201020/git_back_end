package com.demo.back_end_springboot.back_end_springboot.domain.pratice;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Record implements Serializable {
    @EmbeddedId
    private RecordPk recordPk;
    private String accountOutline;
    private BigDecimal cash;
    @Lob
    private StockVolume[] stockVolumes;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.ALWAYS)
    private Timestamp recordTime;

    private BigDecimal total;

    private String visibility;

    public Record() {
    }

    public Record(RecordPk recordPk, String accountOutline, BigDecimal cash, StockVolume[] stockVolumes, Timestamp recordTime, BigDecimal total, String visibility) {
        this.recordPk = recordPk;
        this.accountOutline = accountOutline;
        this.cash = cash;
        this.stockVolumes = stockVolumes;
        this.recordTime = recordTime;
        this.total = total;
        this.visibility = visibility;
    }

    public String getAccountOutline() {
        return accountOutline;
    }

    public void setAccountOutline(String accountOutline) {
        this.accountOutline = accountOutline;
    }

    public RecordPk getRecordPk() {
        return recordPk;
    }

    public void setRecordPk(RecordPk recordPk) {
        this.recordPk = recordPk;
    }


    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

    public StockVolume[] getStockVolumes() {
        return stockVolumes;
    }

    public void setStockVolumes(StockVolume[] stockVolumes) {
        this.stockVolumes = stockVolumes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    @Embeddable
    public static class RecordPk implements Serializable {
        private String account;
        private String date;

        public RecordPk() {
        }

        public RecordPk(String account, String date) {
            this.account = account;
            this.date = date;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
