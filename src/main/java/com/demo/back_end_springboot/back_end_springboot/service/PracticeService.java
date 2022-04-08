package com.demo.back_end_springboot.back_end_springboot.service;

import com.demo.back_end_springboot.back_end_springboot.domain.pratice.HistoryAssetsForm;
import com.demo.back_end_springboot.back_end_springboot.domain.pratice.PracticeForm;
import com.demo.back_end_springboot.back_end_springboot.domain.pratice.Record;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface PracticeService {
    Map<String, Object> getRecordByAccount(String account);

    Map<String, Object> createRecord(String account);

    Map<String, Object> buyStock(PracticeForm practiceForm);

    Map<String, Object> sellStock(PracticeForm practiceForm);

    Map<String, Object> getHistoryAssets(HistoryAssetsForm historyAssetsForm);

    void changeRecordVisibility(String account, String visibility);

    @Modifying
    @Transactional
    void resetRecordByAccount(String account);

    List<Record> getTopList();
}
