package com.example.buaadataplatform.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="push")
public class PushEntity {

    @TableId(type= IdType.AUTO)
    private int pushId;

    private String title;

    private int articleId;

    private int testId;

    private String userIds;

    private String startDate;

    private String endDate;
}
