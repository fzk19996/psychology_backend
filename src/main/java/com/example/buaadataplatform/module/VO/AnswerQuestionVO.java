package com.example.buaadataplatform.module.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerQuestionVO {

    private String question_id;

    private String answer;

    private int score;

    private String que_type;

    private String time_use;

    private  int correct;

    private String type;

    private int index;

    private String start;

    private String end;

}
