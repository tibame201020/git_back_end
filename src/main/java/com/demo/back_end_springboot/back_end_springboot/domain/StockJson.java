package com.demo.back_end_springboot.back_end_springboot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class StockJson implements Serializable {

    @Id
    @JsonProperty(value="Code")
    private String code;

    @JsonProperty(value="Name")
    private String name;

    @JsonProperty(value="TradeVolume")
    private String tradevolume;

    @JsonProperty(value="TradeValue")
    private String tradevalue;

    @JsonProperty(value="OpeningPrice")
    private String openingprice;

    @JsonProperty(value="HighestPrice")
    private String highestprice;

    @JsonProperty(value="LowestPrice")
    private String lowestprice;

    @JsonProperty(value="ClosingPrice")
    private String closingprice;

    @Column(name="change2")
    @JsonProperty(value="Change")
    private String change;
    @Column(name="transaction2")
    @JsonProperty(value="Transaction")
    private String transaction;

    public StockJson() {

    }

    public StockJson(String code, String name, String tradevolume, String tradevalue, String openingprice,
                                   String highestprice, String lowestprice, String closingprice, String change, String transaction) {
        super();
        this.code = code;
        this.name = name;
        this.tradevolume = tradevolume;
        this.tradevalue = tradevalue;
        this.openingprice = openingprice;
        this.highestprice = highestprice;
        this.lowestprice = lowestprice;
        this.closingprice = closingprice;
        this.change = change;
        this.transaction = transaction;
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

    public String getTradevolume() {
        return tradevolume;
    }

    public void setTradevolume(String tradevolume) {
        this.tradevolume = tradevolume;
    }

    public String getTradevalue() {
        return tradevalue;
    }

    public void setTradevalue(String tradevalue) {
        this.tradevalue = tradevalue;
    }

    public String getOpeningprice() {
        return openingprice;
    }

    public void setOpeningprice(String openingprice) {
        this.openingprice = openingprice;
    }

    public String getHighestprice() {
        return highestprice;
    }

    public void setHighestprice(String highestprice) {
        this.highestprice = highestprice;
    }

    public String getLowestprice() {
        return lowestprice;
    }

    public void setLowestprice(String lowestprice) {
        this.lowestprice = lowestprice;
    }

    public String getClosingprice() {
        return closingprice;
    }

    public void setClosingprice(String closingprice) {
        this.closingprice = closingprice;
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
}
