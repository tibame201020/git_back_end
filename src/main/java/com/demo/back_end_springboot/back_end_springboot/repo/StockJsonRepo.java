package com.demo.back_end_springboot.back_end_springboot.repo;

import com.demo.back_end_springboot.back_end_springboot.domain.StockJson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJsonRepo extends JpaRepository<StockJson, String> {
}
