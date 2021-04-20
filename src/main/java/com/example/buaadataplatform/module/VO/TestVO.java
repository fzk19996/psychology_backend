package com.example.buaadataplatform.module.VO;

import com.alibaba.fastjson.JSONObject;
import com.example.buaadataplatform.module.entity.TestEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class TestVO {

    private int testId;

    private String title;

    private String tableId;

    private int experimentId;

    private String startDate;

    private String endDate;

    private List<varVO> varList;

    private int useVideo;

    public TestVO(TestEntity testEntity){
        BeanUtils.copyProperties(testEntity, this);
        this.varList = JSONObject.parseArray(testEntity.getJsonVar(), varVO.class);
    }
}
