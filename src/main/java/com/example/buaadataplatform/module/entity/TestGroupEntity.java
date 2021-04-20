package com.example.buaadataplatform.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("testgroup")
@NoArgsConstructor
public class TestGroupEntity {

    @TableId(value="test_group_id", type= IdType.AUTO)
    private int testGroupId;

    private int testId;

    private int userId;

    public TestGroupEntity(int testId, int userId){
        this.testId = testId;
        this.userId = userId;
    }
}
