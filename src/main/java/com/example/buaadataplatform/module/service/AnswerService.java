package com.example.buaadataplatform.module.service;

import com.example.buaadataplatform.module.VO.*;
import com.example.buaadataplatform.module.entity.AnswerEntity;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface AnswerService {

    public List<AnswerVO> queryAnswerList(int index, int size);

    public boolean adminGetAnswer(int test_id, HttpServletResponse response);

    public QuestionVO queryQuestionInExam(String type, int index, HttpSession session);

    public boolean addAnswer(AnswerQuestionVO answerQuestionVO, HttpSession session);

    public QuestionVO continueExam(HttpSession session);

    public boolean endExam(HttpSession session);

    public RedisAnswerVO queryRedisAnswerVO(HttpSession session);

    public boolean submitAnswer(HttpSession session);

    public ResultVO queryAnswerResult(int answer_id);

    public List<AnswerEntity> queryUserAnswers(int user_id);

    public boolean deleteAnswer(int answer_id);

    public boolean updateAnswer(AnswerEntity answerEntity);

}
