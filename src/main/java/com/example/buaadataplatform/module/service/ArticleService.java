package com.example.buaadataplatform.module.service;

import com.example.buaadataplatform.module.entity.ArticleEntity;

import java.util.List;

public interface ArticleService {

    public List<ArticleEntity> queryArticleAbstractList(int index, int size);

    public ArticleEntity queryArticleById(int id);

    public ArticleEntity queryArticleAbstractById(int id);

    public boolean addArticle(ArticleEntity articleEntity);

    public boolean delArticle(int id);

    public boolean updateArticle(ArticleEntity articleEntity);

    public List<ArticleEntity> mohuQueryArticle(String title);

    public List<ArticleEntity> queryArticleList(int index, int size);

    public int queryArticleCount();

    public List<ArticleEntity> queryPortalArticles(int index, int size);
}
