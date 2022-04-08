package com.demo.back_end_springboot.back_end_springboot.controller.publish;

import com.demo.back_end_springboot.back_end_springboot.domain.Article;
import com.demo.back_end_springboot.back_end_springboot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/publish")
@RestController
public class PublishController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody Article article) {
        try {
            articleService.save(article);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }

    @RequestMapping("/manage")
    public List<Article> findByAccount(@RequestBody String account) {
        return articleService.findByAccount(account);
    }

    @RequestMapping("/deleteArticle")
    public ResponseEntity<Boolean> deleteArticle(@RequestBody Article article) {
        article.setState("delete");
        try {
            articleService.save(article);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }

    @RequestMapping("/restoreArticle")
    public ResponseEntity<Boolean> restoreArticle(@RequestBody Article article) {
        article.setState("publish");
        try {
            articleService.save(article);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }
    }
}
