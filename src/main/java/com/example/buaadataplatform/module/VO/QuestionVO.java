package com.example.buaadataplatform.module.VO;

import com.alibaba.fastjson.JSONObject;
import com.example.buaadataplatform.module.entity.OptionEntity;
import com.example.buaadataplatform.module.entity.QuestionEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class QuestionVO {

    private String questionId;

    private boolean must;

    private String question;

    private String type;

    private List<OptionEntity> options;

    private String picUrl;

    private int timeLimit;

    private String rightAnswer;

    private int index;

    public QuestionVO(QuestionEntity questionEntity){
        BeanUtils.copyProperties(questionEntity, this);
        this.options = (JSONObject.parseArray(new String(questionEntity.getJsonOptions()),OptionEntity.class));
    }

    public QuestionVO(){};


}
