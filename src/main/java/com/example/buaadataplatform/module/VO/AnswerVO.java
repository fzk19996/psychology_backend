package com.example.buaadataplatform.module.VO;

import com.alibaba.fastjson.JSONObject;
import com.example.buaadataplatform.module.entity.AnswerEntity;
import com.example.buaadataplatform.module.entity.OptionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class AnswerVO {

    private int answerId;

    private int userId;

    private int testId;

    private String state;

    private String comment;

    private String test_name;

    private List<AnswerQuestionVO> table_answer_list;

    private List<AnswerQuestionVO> experiment_answer_list;

    private String videoUrl;

    private List<String> scoreList;

    public AnswerVO(AnswerEntity answerEntity){
        BeanUtils.copyProperties(answerEntity, this);
        this.scoreList = JSONObject.parseArray(new String(answerEntity.getJsonScoreList()), String.class);
        this.table_answer_list = JSONObject.parseArray(new String(answerEntity.getJsonTableAnswer()), AnswerQuestionVO.class);
        this.experiment_answer_list = JSONObject.parseArray(new String(answerEntity.getJsonExperimentAnswer()), AnswerQuestionVO.class);
    }


}
