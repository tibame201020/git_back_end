package com.demo.back_end_springboot.back_end_springboot.repo;

import com.demo.back_end_springboot.back_end_springboot.domain.pratice.StockRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRecordRepo extends JpaRepository<StockRecord, Integer> {
    List<StockRecord> findByAccountOrderByRecordTimeDesc(String account);
}
