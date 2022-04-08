package com.demo.back_end_springboot.back_end_springboot.service.impl;

import com.demo.back_end_springboot.back_end_springboot.domain.Article;
import com.demo.back_end_springboot.back_end_springboot.repo.ArticleRepo;
import com.demo.back_end_springboot.back_end_springboot.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepo articleRepo;


    @Override
    public void save(Article article) {
        if (StringUtils.isBlank(article.getState())) {
            article.setState("publish");
        }
        articleRepo.save(article);
    }

    @Override
    public List<Article> findByAccount(String account) {
        return articleRepo.findByAccount(account);
    }

    @Override
    public List<Article> findByPublish() {
        return articleRepo.findByStateAndVisibilityOrderByUpdateTimeDesc("publish", "all");
    }

    @Override
    public List<Article> findOwnArticle(String account) {
        return articleRepo.findByAccountAndStateOrderByUpdateTimeDesc(account, "publish");
    }


}
