package com.example.buaadataplatform.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("experiment")
public class ExperimentEntity {

    @TableId(type= IdType.AUTO)
    private int experimentId;

    private String title;

    private String questionIdList;

//    private List<QuestionEntity> questions;

}
