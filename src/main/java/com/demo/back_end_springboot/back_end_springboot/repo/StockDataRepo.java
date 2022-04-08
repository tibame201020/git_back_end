package com.demo.back_end_springboot.back_end_springboot.repo;

import com.demo.back_end_springboot.back_end_springboot.domain.StockData;
import com.demo.back_end_springboot.back_end_springboot.domain.StockData.StockDataPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StockDataRepo extends JpaRepository<StockData, StockDataPk> {
    List<StockData> findByYearMonthCode(String yearMonthCode);
    List<StockData> findByCodeOutAndYearMonthDateBetweenOrderByYearMonthDate(String codeOut, String startDate,String endDate);
    List<StockData> findByYearMonthCodeOrderByYearMonthDateDesc(String yearMonthCode);
}
