package com.example.buaadataplatform.module.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("PushGroup")
@Data
public class PushGroupEntity {

    @TableId
    private int pushGroupId;

    private int pushId;

    private String userIds;
}
