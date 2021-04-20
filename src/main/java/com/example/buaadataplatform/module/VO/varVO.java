package com.example.buaadataplatform.module.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class varVO{
    private String varName;

    private String expression;

    private List<Float> scores;

    private List<String> comments;
}