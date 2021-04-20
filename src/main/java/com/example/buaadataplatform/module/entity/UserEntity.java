package com.example.buaadataplatform.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class UserEntity {

    @TableId(type = IdType.AUTO)
    private int userid;

    private String username;

    private String password;

    private String register_time;

    private String email;

    private int testday;

    private String avatar;

    private int age;

    private String number;

    private int authority;


}
