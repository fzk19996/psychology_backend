package com.example.buaadataplatform.module.VO;

import com.alibaba.fastjson.JSONObject;
import com.example.buaadataplatform.module.entity.RedisAnswerEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class RedisAnswerVO {

    private List<QuestionVO> tableQuestionVOList;

    private List<QuestionVO> experimentQuestionVOList;

    private List<AnswerQuestionVO> experimentAnswerList;

    private List<AnswerQuestionVO> tableAnswerList;

    private int question_index_t;

    private int question_index_e;

    private int test_id;

    private String videoUrl;

    private int use_video;

    public RedisAnswerVO(RedisAnswerEntity redisAnswerEntity){
        BeanUtils.copyProperties(redisAnswerEntity, this);
        this.tableQuestionVOList = JSONObject.parseArray(new String(redisAnswerEntity.getJson_tableQuestionVOList()),QuestionVO.class);
        this.experimentQuestionVOList = JSONObject.parseArray(new String(redisAnswerEntity.getJson_experimentQuestionVOList()),QuestionVO.class);
        this.tableAnswerList = JSONObject.parseArray(new String(redisAnswerEntity.getJson_tableAnswerList()),AnswerQuestionVO.class);
        this.experimentAnswerList = JSONObject.parseArray(new String(redisAnswerEntity.getJson_experimentAnswerList()),AnswerQuestionVO.class);

    };

}
