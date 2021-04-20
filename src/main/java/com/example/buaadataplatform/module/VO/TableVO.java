package com.example.buaadataplatform.module.VO;

import com.example.buaadataplatform.module.entity.QuestionEntity;
import com.example.buaadataplatform.module.entity.TableEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class TableVO {

    private int tableId;

    private String title;

    private List<QuestionVO> questions;

    public TableVO(TableEntity tableEntity, List<QuestionVO> questions){
        BeanUtils.copyProperties(tableEntity, this);
        this.questions = questions;
    }

}
