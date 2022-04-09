package com.demo.back_end_springboot.back_end_springboot.service;

import com.demo.back_end_springboot.back_end_springboot.domain.CodeParam;
import com.demo.back_end_springboot.back_end_springboot.domain.CompanyInfo;
import com.demo.back_end_springboot.back_end_springboot.domain.News;
import com.demo.back_end_springboot.back_end_springboot.domain.StockJson;

import java.util.Map;

public interface TwseStockApi {
    StockJson[] getCodeNmList(String key);

    CompanyInfo[] getCompanyInfo(String key);

    Map<String, Object> getBasicInfo(CodeParam codeParam);

    boolean checkStockCodeNm(String key);

    News[] getNews();

    String getPriceByCode(String key);
}
