package com.example.buaadataplatform.module.VO;

import lombok.Data;

import java.util.List;

@Data
public class ResultVO {

    private List<String> scoreList;

    private List<String> commentList;

    private List<String> varNameList;

    private String adminComment;

    private String test_name;
}
