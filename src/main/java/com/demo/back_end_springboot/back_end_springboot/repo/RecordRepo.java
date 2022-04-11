package com.demo.back_end_springboot.back_end_springboot.repo;

import com.demo.back_end_springboot.back_end_springboot.domain.pratice.Record;
import com.demo.back_end_springboot.back_end_springboot.domain.pratice.Record.RecordPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepo extends JpaRepository<Record, RecordPk> {
    List<Record> findByAccountOutlineOrderByRecordPk(String accountOutline);
    List<Record> findByAccountOutlineOrderByRecordPkDesc(String accountOutline);
    List<Record> findByAccountOutlineAndVisibilityOrderByRecordPk(String accountOutline, String visibility);

    void deleteByAccountOutline(String accountOutline);

}
