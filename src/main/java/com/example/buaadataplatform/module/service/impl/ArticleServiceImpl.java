package com.example.buaadataplatform.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.buaadataplatform.module.constant.ArticleType;
import com.example.buaadataplatform.module.entity.ArticleEntity;
import com.example.buaadataplatform.module.mapper.xinli.ArticleMapper;
import com.example.buaadataplatform.module.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<ArticleEntity> queryArticleAbstractList(int index, int size) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("article_id","abstract_content","title").eq("type", 2);
        List<ArticleEntity> articleEntities = articleMapper.selectPage(new Page<ArticleEntity>(index, size), queryWrapper).getRecords();
        return articleEntities;
    }

    @Override
    public ArticleEntity queryArticleById(int id) {
        return articleMapper.selectById(id);
    }

    @Override
    public ArticleEntity queryArticleAbstractById(int id) {
        return articleMapper.selectOne(new QueryWrapper<ArticleEntity>().select("article_id","abstract_content","title").eq("article_id",id));
    }

    @Override
    public boolean addArticle(ArticleEntity articleEntity) {
        return articleMapper.insert(articleEntity)==1?true:false;
    }

    @Override
    public boolean delArticle(int id) {
        return articleMapper.deleteById(id)==1?true:false;
    }

    @Override
    public boolean updateArticle(ArticleEntity articleEntity) {
        return articleMapper.updateById(articleEntity)==1?true:false;
    }

    @Override
    public List<ArticleEntity> mohuQueryArticle(String title) {
        return articleMapper.selectList(new QueryWrapper<ArticleEntity>().select("article_id", "title").like("title", title));
    }

    @Override
    public List<ArticleEntity> queryArticleList(int index, int size) {
        List<ArticleEntity> articleEntities = articleMapper.selectPage(new Page<ArticleEntity>(index, size), new QueryWrapper<ArticleEntity>().select("article_id", "title", "author", "create_time")).getRecords();
        return articleEntities;
    }

    @Override
    public int queryArticleCount() {
        return articleMapper.selectCount(null);
    }

    @Override
    public List<ArticleEntity> queryPortalArticles(int index, int size) {
        return articleMapper.selectPage(new Page<ArticleEntity>(index, size), new QueryWrapper<ArticleEntity>()
            .eq("type",2)).getRecords();
    }
}
