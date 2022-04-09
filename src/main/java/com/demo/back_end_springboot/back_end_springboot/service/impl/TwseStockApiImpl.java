package com.demo.back_end_springboot.back_end_springboot.service.impl;

import com.demo.back_end_springboot.back_end_springboot.domain.*;
import com.demo.back_end_springboot.back_end_springboot.domain.StockData.StockDataPk;
import com.demo.back_end_springboot.back_end_springboot.repo.StockDataRepo;
import com.demo.back_end_springboot.back_end_springboot.service.TwseStockApi;
import com.demo.back_end_springboot.back_end_springboot.tasks.ScheduledTasks;
import com.demo.back_end_springboot.back_end_springboot.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private StockDataRepo stockDataRepo;


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
            return Arrays.stream(ScheduledTasks.getCompanyInfo()).filter(companyInfo -> companyInfo.getCode().equals(finalKey)).toArray(CompanyInfo[]::new);
        }
    }

    @Override
    public Map<String, Object> getBasicInfo(CodeParam codeParam) {
        Map<String, Object> rtnMap = new HashMap<>();
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
        rtnMap.put("result", result);
        rtnMap.put("status", status);

        return rtnMap;
    }

    private List<StockData> getStockList(CodeParam codeParam) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate start = LocalDate.parse(codeParam.getStartDate(), formatter);
        LocalDate end = LocalDate.parse(codeParam.getEndDate(), formatter).plusDays(1);
        String code = codeParam.getCode();
        String beforeYearMonthCode = "";
        String thisMonth = DateUtil.getThisMonth();
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            String yearMonthCode = date.format(DateTimeFormatter.ofPattern("yyyy/MM")) + code;
            if (!beforeYearMonthCode.equals(yearMonthCode)) {

                if (yearMonthCode.equals(thisMonth + code)) {
                    String dateFormat = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                    StockBasicInfo stockBasicInfo = getInfoUrl(dateFormat, code);
                    List<StockData> stockDataList = translateJsonData(stockBasicInfo.getData(), code);
                    long apiDateSize = stockDataList.size();
                    long dbCount = stockDataRepo.countByYearMonthCode(yearMonthCode);
                    if (apiDateSize != dbCount) {
                        stockDataRepo.saveAll(stockDataList);
                    }
                }

                List<StockData> stockDataListByMonth = stockDataRepo.findByYearMonthCode(yearMonthCode);
                if (null == stockDataListByMonth || stockDataListByMonth.size() == 0) {
                    saveDataFromUrl(date, code);
                }
                beforeYearMonthCode = yearMonthCode;
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

        for (StockJson stockJson : ScheduledTasks.getStockJsons()) {
            if (stockJson.getCode().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public News[] getNews() {
        return ScheduledTasks.getNews();
    }

    @Override
    public String getPriceByCode(String code) {
        return Arrays.stream(ScheduledTasks.getStockJsons()).filter(stockJson -> stockJson.getCode().equals(splitCode(code))).findFirst().get().getClosingprice();
    }

    private String splitCode(String code) {
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
