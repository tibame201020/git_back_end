package com.demo.back_end_springboot.back_end_springboot.tasks;

import com.demo.back_end_springboot.back_end_springboot.domain.StockJson;
import com.demo.back_end_springboot.back_end_springboot.repo.StockJsonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class ScheduledTasks {
    private static final String STOCK_DAY_ALL_URL = "https://openapi.twse.com.tw/v1/exchangeReport/STOCK_DAY_ALL";
    private final RestTemplate REST_TEMPLATE = new RestTemplate();
    @Autowired
    private StockJsonRepo stockJsonRepo;

    private static StockJson[] stockJsons;

    @Scheduled(fixedRate = 1000*60*60*24)
    public void getPriceFromOpenTwseApi() {
        StockJson[] stockJsons = REST_TEMPLATE.getForObject(STOCK_DAY_ALL_URL, StockJson[].class);
        // stockJsonRepo.saveAll(Arrays.asList(stockJsons));
        ScheduledTasks.stockJsons = stockJsons;
    }

    public static StockJson[] getStockJsons () {
        return stockJsons;
    }
}
