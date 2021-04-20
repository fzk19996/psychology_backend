package com.example.buaadataplatform.module.entity;

import lombok.Data;

@Data
public class OptionEntity {

    private int option_id;

    private boolean allowAddReasonStatus;

    private String content;

    private String pictureList;

    private String score;

    private int question_id;

    private String picList;
}
