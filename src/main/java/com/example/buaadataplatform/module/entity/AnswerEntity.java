package com.example.buaadataplatform.module.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.buaadataplatform.module.VO.AnswerVO;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@TableName("answer")
public class AnswerEntity {

    @TableId(type = IdType.AUTO)
    private int answerId;

    private int userId;

    private int testId;

    private String comment;

    private String jsonScoreList;

    private String state;

    private String jsonTableAnswer;

    private String jsonExperimentAnswer;

    private String videoUrl;

    private String createTime;

    private String scoreList;

    public AnswerEntity(){};

    public AnswerEntity(AnswerVO answerVO){
        BeanUtils.copyProperties(answerVO, this);
        this.jsonScoreList = JSON.toJSONString(answerVO.getScoreList());
        this.jsonTableAnswer = JSON.toJSONString(answerVO.getTable_answer_list());
        this.jsonExperimentAnswer = JSON.toJSONString(answerVO.getExperiment_answer_list());
        this.scoreList = String.join(";", answerVO.getScoreList());
        this.state = "待审阅";
    }
}
