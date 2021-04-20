package com.example.buaadataplatform.module.entity;

import lombok.Data;

import java.util.List;

@Data
public class ExperimentAnswerEntity {

    private int experiment_id;

    private List<ExperimentAnsQuestionEntity> answer_list;
}
