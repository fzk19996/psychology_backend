package com.example.buaadataplatform.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("tables")
public class TableEntity {

    @TableId(type = IdType.AUTO)
    private int tableId;

    private String title;

    private String questionIdList;

//    private List<QuestionEntity> questions;
}
