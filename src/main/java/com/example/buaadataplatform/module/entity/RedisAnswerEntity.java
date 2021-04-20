package com.example.buaadataplatform.module.entity;

import com.alibaba.fastjson.JSONObject;
import com.example.buaadataplatform.module.VO.AnswerQuestionVO;
import com.example.buaadataplatform.module.VO.QuestionVO;
import com.example.buaadataplatform.module.VO.RedisAnswerVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class RedisAnswerEntity {
    private String json_tableQuestionVOList;

    private String json_experimentQuestionVOList;

    private String json_experimentAnswerList;

    private String json_tableAnswerList;

    private int question_index_t;

    private int question_index_e;

    private int test_id;

    private String videoUrl;

    private int use_video;

    public RedisAnswerEntity(RedisAnswerVO redisAnswerVO){
        BeanUtils.copyProperties(redisAnswerVO, this);
        this.json_tableAnswerList = JSONObject.toJSONString(redisAnswerVO.getTableAnswerList());
        this.json_experimentAnswerList = JSONObject.toJSONString(redisAnswerVO.getExperimentAnswerList());
        this.json_tableQuestionVOList = JSONObject.toJSONString(redisAnswerVO.getTableQuestionVOList());
        this.json_experimentQuestionVOList = JSONObject.toJSONString(redisAnswerVO.getExperimentQuestionVOList());
    }

}
