package com.demo.back_end_springboot.back_end_springboot.controller.read;

import com.demo.back_end_springboot.back_end_springboot.domain.Article;
import com.demo.back_end_springboot.back_end_springboot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/read")
public class ReadController {

    @Autowired
    ArticleService articleService;

    @RequestMapping("all")
    public List<Article> getAll() {
        return articleService.findByPublish();
    }

    @RequestMapping("own")
    public List<Article> getOwnArticle(@RequestBody String account) {
        return articleService.findOwnArticle(account);
    }
}
