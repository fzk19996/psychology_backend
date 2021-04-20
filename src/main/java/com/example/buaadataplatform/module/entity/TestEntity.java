package com.example.buaadataplatform.module.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.buaadataplatform.module.VO.TestVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@TableName("test")
@NoArgsConstructor
public class TestEntity {

    @TableId(value="test_id", type= IdType.AUTO)
    private int testId;

    private String title;

    private String tableId;

    private int experimentId;

    private String startDate;

    private String endDate;

    private String jsonVar;

    private int useVideo;

    public TestEntity(TestVO testVO){
        BeanUtils.copyProperties(testVO, this);
        this.jsonVar = JSON.toJSONString(testVO.getVarList());
    }

}
