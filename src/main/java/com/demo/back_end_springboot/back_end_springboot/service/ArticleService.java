package com.demo.back_end_springboot.back_end_springboot.service;

import com.demo.back_end_springboot.back_end_springboot.domain.Article;

import java.util.List;

public interface ArticleService {
    void save(Article article);

    List<Article> findByAccount(String account);

    List<Article> findByPublish();

    List<Article> findOwnArticle(String account);
}
