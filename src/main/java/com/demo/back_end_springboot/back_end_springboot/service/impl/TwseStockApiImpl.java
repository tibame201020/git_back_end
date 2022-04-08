package com.demo.back_end_springboot.back_end_springboot.service.impl;

import com.demo.back_end_springboot.back_end_springboot.domain.CodeParam;
import com.demo.back_end_springboot.back_end_springboot.domain.CompanyInfo;
import com.demo.back_end_springboot.back_end_springboot.domain.News;
import com.demo.back_end_springboot.back_end_springboot.domain.RestTemplateWithProxy;
import com.demo.back_end_springboot.back_end_springboot.domain.StockBasicInfo;
import com.demo.back_end_springboot.back_end_springboot.domain.StockData;
import com.demo.back_end_springboot.back_end_springboot.domain.StockData.StockDataPk;
import com.demo.back_end_springboot.back_end_springboot.domain.StockJson;
import com.demo.back_end_springboot.back_end_springboot.repo.StockDataRepo;
import com.demo.back_end_springboot.back_end_springboot.repo.StockJsonRepo;
import com.demo.back_end_springboot.back_end_springboot.service.TwseStockApi;
import com.demo.back_end_springboot.back_end_springboot.tasks.ScheduledTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TwseStockApiImpl implements TwseStockApi {


    private static final RestTemplate REST_TEMPLATE;
    static {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        REST_TEMPLATE = new RestTemplate(factory);
    }
    private static final String INFO_URL = "https://www.twse.com.tw/en/exchangeReport/STOCK_DAY?response=json&date=%s&stockNo=%s";
    private static final String COMPANY_INFO_URL = "https://openapi.twse.com.tw/v1/opendata/t187ap03_L";
    private static final String NEWS_URL = "https://openapi.twse.com.tw/v1/news/newsList";
    private static final CompanyInfo[] ALL_COMPANY_TODAY_INFO = REST_TEMPLATE.getForObject(COMPANY_INFO_URL, CompanyInfo[].class);
    private static final News[] ALL_NEWS = REST_TEMPLATE.getForObject(NEWS_URL, News[].class);

    @Autowired
    private StockDataRepo stockDataRepo;
    @Autowired
    private StockJsonRepo stockJsonRepo;

    @Override
    public StockJson[] getCodeNmList(String key) {
        return Arrays.stream(ScheduledTasks.getStockJsons()).filter(stockJson -> stockJson.getCode().contains(key) || stockJson.getName().contains(key)).toArray(StockJson[]::new);
    }

    @Override
    public CompanyInfo[] getCompanyInfo(String key) {
        if (!checkStockCodeNm(key)) {
            return null;
        } else {
            if (key.contains("-")) {
                key = key.split("-")[0].trim();
            }
            String finalKey = key;
            return Arrays.stream(ALL_COMPANY_TODAY_INFO).filter(companyInfo -> companyInfo.getCode().equals(finalKey)).toArray(CompanyInfo[]::new);
        }
    }

    @Override
    public Map<String,Object> getBasicInfo(CodeParam codeParam) {
        Map<String,Object> rtnMap = new HashMap<>();
        boolean status;
        Object result;
        if (!checkStockCodeNm(codeParam.getCode())) {
            result = "stock code is un-correct";
            status = false;
        } else {
            List<StockData> stockDataList = getStockList(codeParam);
            status = true;
            result = stockDataList;
        }
        rtnMap.put("result",result);
        rtnMap.put("status", status);

        return rtnMap;
    }

    private List<StockData> getStockList(CodeParam codeParam) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate start = LocalDate.parse(codeParam.getStartDate(), formatter);
        LocalDate end = LocalDate.parse(codeParam.getEndDate(), formatter).plusDays(1);
        String code = codeParam.getCode();
        for (LocalDate date = start; date.isBefore(end); date = date.plusMonths(1)) {
            String yearMonthCode = date.format(DateTimeFormatter.ofPattern("yyyy/MM")) + code;
            List<StockData> stockDataListByMonth = stockDataRepo.findByYearMonthCode(yearMonthCode);
            if (null == stockDataListByMonth || stockDataListByMonth.size() == 0) {
                saveDataFromUrl(date, code);
            }
        }
        return stockDataRepo.findByCodeOutAndYearMonthDateBetweenOrderByYearMonthDate(code, codeParam.getStartDate(), codeParam.getEndDate());
    }

    private void saveDataFromUrl(LocalDate date, String code) {
        String dateFormat = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        StockBasicInfo stockBasicInfo = getInfoUrl(dateFormat, code);
        List<StockData> stockDataList = translateJsonData(stockBasicInfo.getData(), code);
        stockDataRepo.saveAll(stockDataList);
    }

    private List<StockData> translateJsonData(String[][] data, String code) {
        List<StockData> stockDataList = new ArrayList<>();
        for (String[] dataInfo : data) {
            StockData stockData = translateStockData(dataInfo, code);
            stockDataList.add(stockData);
        }
        return stockDataList;
    }

    private StockData translateStockData(String[] dataInfo, String code) {
        StockDataPk stockDataPk = new StockDataPk();
        stockDataPk.setDate(dataInfo[0]);
        stockDataPk.setCode(code);
        StockData stockData = new StockData();
        stockData.setStockDataPk(stockDataPk);
        stockData.setTradeVolume(dataInfo[1]);
        stockData.setTradeValue(dataInfo[2]);
        stockData.setOpeningPrice(dataInfo[3]);
        stockData.setLowestPrice(dataInfo[4]);
        stockData.setHighestPrice(dataInfo[5]);
        stockData.setClosingPrice(dataInfo[6]);
        stockData.setChange(dataInfo[7]);
        stockData.setTransaction(dataInfo[8]);
        stockData.setYearMonthCode(dataInfo[0].substring(0, 7) + code);
        stockData.setYearMonthDate(dataInfo[0]);
        stockData.setCodeOut(code);
        return stockData;
    }

    @Override
    public boolean checkStockCodeNm(String key) {
        if (key.contains("-")) {
            key = key.split("-")[0].trim();
        }

        if (stockJsonRepo.findById(key).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public News[] getNews() {
        return ALL_NEWS;
    }

    @Override
    public String getPriceByCode(String code) {
        try {
            return stockJsonRepo.findById(code).get().getClosingprice();
        } catch (Exception e) {
            return "no price find";
        }

    }

    private String splitCode (String code) {
        if (code.contains("-")) {
            code = code.split("-")[0].trim();
        }
        return code.trim();
    }

    private StockBasicInfo getInfoUrl(String date, String code) {
        String url = String.format(INFO_URL, date, code);
        return REST_TEMPLATE.getForObject(url, StockBasicInfo.class);
    }
}
