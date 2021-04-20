package com.example.buaadataplatform.module.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.buaadataplatform.module.VO.QuestionVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@TableName("question")
public class QuestionEntity {

    @TableId
    private String questionId;

    private boolean must;

    private String question;

    private String type;

    private String jsonOptions;

    private String picUrl;

    private int timeLimit;

    private String rightAnswer;

    public QuestionEntity(QuestionVO questionVO){
        BeanUtils.copyProperties(questionVO, this);
        this.jsonOptions = JSON.toJSONString(questionVO.getOptions());
    }

    public QuestionEntity(){}

}
