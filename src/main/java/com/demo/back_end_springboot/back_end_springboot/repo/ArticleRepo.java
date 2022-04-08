package com.demo.back_end_springboot.back_end_springboot.repo;

import com.demo.back_end_springboot.back_end_springboot.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {
    List<Article> findByAccount(String account);
    List<Article> findByStateAndVisibilityOrderByUpdateTimeDesc(String state, String visibility);
    List<Article> findByAccountAndStateOrderByUpdateTimeDesc(String account, String state);
}
