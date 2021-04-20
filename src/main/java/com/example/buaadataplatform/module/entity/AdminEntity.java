package com.example.buaadataplatform.module.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("admin")
public class AdminEntity {

    private int admin_id;

    private String admin_name;

    private String password;

    private String admin;

    private String avatar;

    private Date create_time;

    private String email;

}
