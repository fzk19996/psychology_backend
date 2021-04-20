package com.example.buaadataplatform.module.entity;

import lombok.Data;

@Data
public class DiaryEntity {

    private int diary_id;

    private int user_id;

    private String title;

    private String content;

}
