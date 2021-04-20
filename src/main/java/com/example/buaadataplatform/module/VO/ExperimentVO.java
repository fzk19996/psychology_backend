package com.example.buaadataplatform.module.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.buaadataplatform.module.entity.ExperimentEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class ExperimentVO {

    @TableId(type= IdType.AUTO)
    private int experimentId;

    private String title;

    private List<QuestionVO> questions;

    public ExperimentVO(ExperimentEntity experimentEntity, List<QuestionVO> questions){
        BeanUtils.copyProperties(experimentEntity, this);
        this.questions = questions;
    }

}
