package com.example.buaadataplatform.module.controller;

import com.example.buaadataplatform.common.VO.AjaxResult;
import com.example.buaadataplatform.module.entity.ArticleEntity;
import com.example.buaadataplatform.module.entity.PushEntity;
import com.example.buaadataplatform.module.service.ArticleService;
import com.example.buaadataplatform.module.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:8080", maxAge = 3600)
@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping("/article/addarticle")
    public AjaxResult addArticle(@RequestBody ArticleEntity articleEntity){
        return articleService.addArticle(articleEntity)?AjaxResult.okMessage("添加文章成功"):AjaxResult.errorMsg("添加文章失败");
    }

    @PostMapping("/article/queryArticleAbstractList")
    public AjaxResult queryArticleAbstractList(int index, int size){
        return AjaxResult.ok(articleService.queryArticleAbstractList(index, size));
    }

    @RequestMapping(value="/article/queryArticleById/{article_id}", method= RequestMethod.GET)
    public AjaxResult queryArticleById(@PathVariable int article_id){
        return AjaxResult.ok(articleService.queryArticleById(article_id));
    }

    @RequestMapping(value="/article/queryArticleAbstractById/{article_id}", method= RequestMethod.GET)
    public AjaxResult queryArticleAbstractById(@PathVariable int article_id){
        return AjaxResult.ok(articleService.queryArticleAbstractById(article_id));
    }

    @PostMapping("/article/updateArticle")
    public AjaxResult updateArticle(@RequestBody ArticleEntity articleEntity){
        return articleService.updateArticle(articleEntity)?AjaxResult.okMessage("更新文章成功"):AjaxResult.errorMsg("更新文章失败");
    }

    @PostMapping("/article/mohuQueryArticle")
    public AjaxResult mohuQueryArticle(String title){
        return AjaxResult.ok(articleService.mohuQueryArticle(title));
    }

    @PostMapping("/article/queyrArticleList")
    public AjaxResult queyrArticleList(int index, int size){
        return AjaxResult.ok(articleService.queryArticleList(index, size));
    }

    @PostMapping("/article/deleteArticle")
    public AjaxResult deleteArticle(int article_id){
        return articleService.delArticle(article_id)?AjaxResult.ok("删除成功！"):AjaxResult.errorMsg("删除失败");
    }

    @PostMapping("/article/queryArticleCount")
    public AjaxResult queryArticleCount(){
        return AjaxResult.ok(articleService.queryArticleCount());
    }

    @PostMapping("/article/queryPortalArticles")
    public AjaxResult queryPortalArticles(int index ,int size){
        return AjaxResult.ok(articleService.queryPortalArticles(index, size));
    }

}
