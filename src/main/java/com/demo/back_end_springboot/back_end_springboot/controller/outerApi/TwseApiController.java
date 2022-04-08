package com.demo.back_end_springboot.back_end_springboot.controller.outerApi;

import com.demo.back_end_springboot.back_end_springboot.domain.CodeParam;
import com.demo.back_end_springboot.back_end_springboot.domain.CompanyInfo;
import com.demo.back_end_springboot.back_end_springboot.domain.News;
import com.demo.back_end_springboot.back_end_springboot.domain.StockJson;
import com.demo.back_end_springboot.back_end_springboot.service.TwseStockApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/twse")
@RestController
public class TwseApiController {
    @Autowired
    TwseStockApi twseStockApi;

    @RequestMapping("/quickSearch")
    public StockJson[] quickSearch(@RequestBody String key) {
        return twseStockApi.getCodeNmList(key);
    }

    @RequestMapping("/getBasicInfo")
    public ResponseEntity<Map<String,Object>> getBasicInfo(@RequestBody CodeParam codeParam) {
        return new ResponseEntity<>(twseStockApi.getBasicInfo(codeParam), HttpStatus.OK);
    }

    @RequestMapping("/getCompanyInfo")
    public CompanyInfo[] getCompanyInfo(@RequestBody String key) {
        return twseStockApi.getCompanyInfo(key);
    }

    @RequestMapping("/checkCodeNm")
    public ResponseEntity<Boolean> checkCodeNm (@RequestBody String codeNm) {
        return new ResponseEntity<>(!twseStockApi.checkStockCodeNm(codeNm.trim()), HttpStatus.OK);
    }

    @RequestMapping("/getNews")
    public News[] getNews() {
        return twseStockApi.getNews();
    }

    @RequestMapping("/getPriceByCode")
    public String getPriceByCode(@RequestBody String code) {
        if (twseStockApi.checkStockCodeNm(code.trim())) {
            return twseStockApi.getPriceByCode(code.trim());
        } else {
            return "";
        }
    }
}
