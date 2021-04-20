package com.example.buaadataplatform.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="article")
public class ArticleEntity {

    @TableId(type= IdType.AUTO)
    private int articleId;

    private String title;

    private String abstractContent;

    private String content;

    private String author;

    private String createTime;

    private int type;

}
