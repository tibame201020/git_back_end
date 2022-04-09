package com.demo.back_end_springboot.back_end_springboot.tasks;

import com.demo.back_end_springboot.back_end_springboot.domain.CompanyInfo;
import com.demo.back_end_springboot.back_end_springboot.domain.News;
import com.demo.back_end_springboot.back_end_springboot.domain.StockJson;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {
    private static final String STOCK_DAY_ALL_URL = "https://openapi.twse.com.tw/v1/exchangeReport/STOCK_DAY_ALL";
    private static final String NEWS_URL = "https://openapi.twse.com.tw/v1/news/newsList";
    private static final String COMPANY_INFO_URL = "https://openapi.twse.com.tw/v1/opendata/t187ap03_L";
    private final RestTemplate REST_TEMPLATE = new RestTemplate();

    private static StockJson[] stockJsons;
    private static News[] news;
    private static CompanyInfo[] companyInfoArray;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void getPriceFromOpenTwseApi() {
        ScheduledTasks.stockJsons = REST_TEMPLATE.getForObject(STOCK_DAY_ALL_URL, StockJson[].class);
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void getNewsFromOpenTwseApi() {
        ScheduledTasks.news = REST_TEMPLATE.getForObject(NEWS_URL, News[].class);
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void getCompanyInfoFromOpenTwseApi() {
        ScheduledTasks.companyInfoArray = REST_TEMPLATE.getForObject(COMPANY_INFO_URL, CompanyInfo[].class);
    }

    public static StockJson[] getStockJsons() {
        return stockJsons;
    }

    public static News[] getNews() {
        return news;
    }

    public static CompanyInfo[] getCompanyInfo() {
        return companyInfoArray;
    }
}
