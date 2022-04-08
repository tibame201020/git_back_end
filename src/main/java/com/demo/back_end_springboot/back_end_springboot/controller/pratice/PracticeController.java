package com.demo.back_end_springboot.back_end_springboot.controller.pratice;

import com.demo.back_end_springboot.back_end_springboot.domain.pratice.HistoryAssetsForm;
import com.demo.back_end_springboot.back_end_springboot.domain.pratice.PracticeForm;
import com.demo.back_end_springboot.back_end_springboot.domain.pratice.Record;
import com.demo.back_end_springboot.back_end_springboot.service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/practice")
public class PracticeController {

    @Autowired
    PracticeService practiceService;

    @RequestMapping("/getSelfRecord")
    public Map<String, Object> getSelfRecord(@RequestBody String account) {
        return practiceService.getRecordByAccount(account);
    }

    @RequestMapping("/createSelfRecord")
    public Map<String, Object> createSelfRecord(@RequestBody String account) {
        return practiceService.createRecord(account);
    }

    @RequestMapping("/buy")
    public Map<String, Object> buy(@RequestBody PracticeForm practiceForm) {
        practiceForm.setBehavior("buy");
        return practiceService.buyStock(practiceForm);
    }

    @RequestMapping("/sell")
    public Map<String, Object> sell(@RequestBody PracticeForm practiceForm) {
        practiceForm.setBehavior("sell");
        return practiceService.sellStock(practiceForm);
    }

    @RequestMapping("/getHistory")
    public Map<String, Object> getHistoryAssetsByAccountAndDateRange(@RequestBody HistoryAssetsForm historyAssetsForm) {
        return practiceService.getHistoryAssets(historyAssetsForm);
    }

    @RequestMapping("/changeVisibility")
    public Map<String, Object> changeVisibility(@RequestBody Record record) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            practiceService.changeRecordVisibility(record.getAccountOutline(), record.getVisibility());
            rtnMap.put("status", true);
            rtnMap.put("msg", "the visibility has been change to " + record.getVisibility());
        } catch (Exception e) {
            rtnMap.put("status", false);
            rtnMap.put("msg", e.getMessage());
        }
        return rtnMap;
    }

    @RequestMapping("/resetRecord")
    public Map<String, Object> resetRecord(@RequestBody String account) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            practiceService.resetRecordByAccount(account);
            rtnMap.put("status", true);
            rtnMap.put("msg", "ur record has been reset");
        } catch (Exception e) {
            rtnMap.put("status", false);
            rtnMap.put("msg", e.getMessage());
        }
        return rtnMap;
    }

    @RequestMapping("/getTopList")
    public List<Record> getTopList() {
        return practiceService.getTopList();
    }


}
