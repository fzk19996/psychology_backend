package com.example.buaadataplatform.module.controller;

import com.example.buaadataplatform.common.VO.AjaxResult;
import com.example.buaadataplatform.module.VO.AnswerQuestionVO;
import com.example.buaadataplatform.module.VO.AnswerVO;
import com.example.buaadataplatform.module.VO.QuestionVO;
import com.example.buaadataplatform.module.VO.RedisAnswerVO;
import com.example.buaadataplatform.module.constant.Authority;
import com.example.buaadataplatform.module.entity.AnswerEntity;
import com.example.buaadataplatform.module.service.AnswerService;
import com.example.buaadataplatform.module.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @Autowired
    TestService testService;

    @PostMapping("/answer/addAnswer")
    public AjaxResult addAnswer(@RequestBody AnswerQuestionVO answerQuestionVO, HttpSession session){
        if(answerService.addAnswer(answerQuestionVO, session))
            return AjaxResult.okMessage("提交答案成功");
        else
            return AjaxResult.errorMsg("提交答案失败");
    }

    @PostMapping("/answer/queryExamQuestion")
    public AjaxResult queryExamQuestion(String type, int index, HttpSession session){
        QuestionVO questionVO = answerService.queryQuestionInExam(type, index, session);
        if(questionVO==null)
            return AjaxResult.errorMsg("获取题目失败");
        else
            return AjaxResult.ok(questionVO);
    }

    @PostMapping("/answer/beginExam")
    public AjaxResult beginExam(int test_id, HttpSession session){
        return AjaxResult.ok(testService.beginExam(test_id, session));
    }

    @PostMapping("/answer/endExam")
    public AjaxResult endExam(HttpSession session){
        return answerService.endExam(session)?AjaxResult.ok():AjaxResult.errorMsg("结束考试失败");
    }

    @PostMapping("/answer/continueExam")
    public AjaxResult continueExam(HttpSession session){
        QuestionVO questionVO = answerService.continueExam(session);
        if(questionVO==null)
            return AjaxResult.errorMsg("继续考试失败");
        else
            return AjaxResult.ok(questionVO);
    }

    @PostMapping("/answer/queryRedisAnswerVO")
    public AjaxResult queryRedisAnswerVO(HttpSession session){
        RedisAnswerVO redisAnswerVO = answerService.queryRedisAnswerVO(session);
        if(redisAnswerVO==null)
            return AjaxResult.errorMsg("查询考试状态失败");
        else
            return AjaxResult.ok(redisAnswerVO);
    }

    @PostMapping("/answer/submitAnswer")
    public AjaxResult submitAnswer(HttpSession session){
        return answerService.submitAnswer(session)?AjaxResult.ok("提交试卷成功"):AjaxResult.errorMsg("提交试卷失败");
    }

    @PostMapping("/answer/queryAnswerResult")
    public AjaxResult queryAnswerResult(int answer_id){
        return AjaxResult.ok(answerService.queryAnswerResult(answer_id));
    }

    @PostMapping("/answer/queryUserAnswers")
    public AjaxResult queryUserAnswers(HttpSession session){
//        int user_id = 3;
        int user_id = (Integer)session.getAttribute("user_id");
        int authority = (Integer)session.getAttribute("authority");
        if(authority <= Authority.NORMAL_USER){
            return AjaxResult.errorMsg("查看成绩权限不够");
        }
        return AjaxResult.ok(answerService.queryUserAnswers(user_id));
    }

    @PostMapping("/answer/deleteAnswer")
    public AjaxResult deleteAnswer(int answer_id){
        return answerService.deleteAnswer(answer_id)?AjaxResult.ok("删除成功"):AjaxResult.errorMsg("删除失败");
    }

    @PostMapping("/answer/updateAnswer")
    public AjaxResult updateAnswer(@RequestBody AnswerEntity answerEntity){
        return answerService.updateAnswer(answerEntity)?AjaxResult.ok("更新成功"):AjaxResult.errorMsg("更新失败");
    }
}

