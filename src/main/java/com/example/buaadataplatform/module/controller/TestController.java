package com.example.buaadataplatform.module.controller;

import com.example.buaadataplatform.common.VO.AjaxResult;
import com.example.buaadataplatform.module.VO.AnswerVO;
import com.example.buaadataplatform.module.VO.QuestionVO;
import com.example.buaadataplatform.module.VO.TestGroupsVO;
import com.example.buaadataplatform.module.VO.TestVO;
import com.example.buaadataplatform.module.entity.*;
import com.example.buaadataplatform.module.service.AnswerService;
import com.example.buaadataplatform.module.service.TestService;
import com.netflix.ribbon.proxy.annotation.Http;
import org.aspectj.weaver.ast.Test;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    AnswerService answerService;

    @PostMapping("/psychology/addtest")
    public AjaxResult addTest(@RequestBody TestVO testVO){
        return testService.addTest(testVO)?AjaxResult.okMessage("添加测试成功"):AjaxResult.errorMsg("添加测试失败");
    }

    @PostMapping("/psychology/test")
    public AjaxResult queryTestList(int index, int limit){
        return AjaxResult.ok(testService.queryTestList(index,limit));
    }

    @GetMapping("/test/queryAnswerList")
    public AjaxResult queryAnswerList(int index, int size){
        return AjaxResult.ok(answerService.queryAnswerList(index, size));
    }

    @RequestMapping(value="/test/deleteTestById", method=RequestMethod.GET)
    public AjaxResult deleteTestById(int test_id){
        return testService.deleteTest(test_id)?AjaxResult.okMessage("删除测试成功"):AjaxResult.errorMsg("删除测试失败");
    }

    @GetMapping(value="/test/queryTestByTitle")
    public AjaxResult queryTestByTitle(String title){
        return AjaxResult.ok(testService.queryTestByTitle(title));
    }

    @GetMapping(value="/test/queryTableByTitle")
    public AjaxResult queryTableByTitle(String title){
        return AjaxResult.ok(testService.queryTableByTtile(title));
    }

    @GetMapping(value="/test/queryExperimentByTitle")
    public AjaxResult queryExperimentByTitle(String title){
        return AjaxResult.ok(testService.queryExperimentByTitle(title));
    }

    @PostMapping(value="/test/addTable")
    public AjaxResult addTable(@RequestBody  TableEntity tableEntity){
        return testService.addTable(tableEntity)?AjaxResult.okMessage("添加量表成功"):AjaxResult.errorMsg("添加量表失败");
    }

    @PostMapping(value="/test/addExperiment")
    public AjaxResult addExperiment(@RequestBody  ExperimentEntity experimentEntity){
        return testService.addExperiment(experimentEntity)?AjaxResult.okMessage("添加测试成功"):AjaxResult.errorMsg("添加测试失败");
    }

    @PostMapping(value="/test/getTablesByIdList")
    public AjaxResult getTablesByIdList(String table_id_list){
        return AjaxResult.ok(testService.queryTablesByIdList(table_id_list));
    }

    @PostMapping(value="/test/updateAnswer")
    public AjaxResult updateAnswer(@RequestBody AnswerEntity answerEntity){
        return testService.updateAnswer(answerEntity)?AjaxResult.okMessage("修改答案成功"):AjaxResult.errorMsg("修改答案失败");
    }

    @GetMapping(value="/test/queryTableById")
    public AjaxResult queryTableById(int table_id){
        return AjaxResult.ok(testService.queryTableVOById(table_id));
    }

    @GetMapping(value="/test/queryExperimentById")
    public AjaxResult queryExperimentById(int experiment_id){return AjaxResult.ok(testService.queryExperimentVOById(experiment_id));}

    @PostMapping(value="/test/updateExperiment")
    public AjaxResult updateExperiment(@RequestBody  ExperimentEntity experimentEntity){
        return testService.updateExperiment(experimentEntity)?AjaxResult.okMessage("修改实验成功"):AjaxResult.errorMsg("修改实验失败");
    }

    @PostMapping(value="/test/updateTable")
    public AjaxResult updateTable(@RequestBody  TableEntity tableEntity){
        return testService.updateTable(tableEntity)?AjaxResult.okMessage("修改实验成功"):AjaxResult.errorMsg("修改实验失败");
    }

    @PostMapping(value="/test/addQuestion")
    public AjaxResult addQuestion(@RequestBody QuestionVO questionVO){
        return testService.addQuestion(questionVO)?AjaxResult.okMessage("添加题目成功"):AjaxResult.errorMsg("添加题目失败");
    }

    @PostMapping(value="/test/downloadAllAnswer")
    public String downloadAllAnswer(int test_id, HttpServletResponse response){
        if(answerService.adminGetAnswer(test_id,response)) {
            return "下载成功";
        }else{
            return "下载失败";
        }
    }

    @PostMapping(value="/test/queryQuestionList")
    public AjaxResult queryQuestionList(int index, int size){
        return AjaxResult.ok(testService.queryQuestionList(index, size));
    }

    @PostMapping(value="/test/mohuQueryQuestion")
    public AjaxResult mohuQueryQuestion(String question_id){
        return AjaxResult.ok(testService.mohuQueryQuestion(question_id));
    }

    @PostMapping(value="/test/mohuQueryTest")
    public AjaxResult mohuQueryTest(String title){
        return AjaxResult.ok(testService.mohuQueryTest(title));
    }

    @PostMapping(value="/test/deleteQuestion")
    public AjaxResult deleteQuestion(String question_id){
        return testService.deleteQuestion(question_id)?AjaxResult.okMessage("删除题目成功"):AjaxResult.errorMsg("删除题目失败");
    }

    @PostMapping(value="/test/queryQuestionById")
    public AjaxResult queryQuestionById(String question_id){
        return AjaxResult.ok(testService.queryQuestionVOById(question_id));
    }

    @PostMapping(value="/test/updateQuestion")
    public AjaxResult updateQuestion(@RequestBody  QuestionVO questionVO){
        return testService.updateQuetion(questionVO)?AjaxResult.okMessage("更新题目成功"):AjaxResult.errorMsg("更新题目失败");
    }

    @GetMapping(value="/test/queryQuestionCount")
    public AjaxResult queryQuestionCount(){
        return AjaxResult.ok(testService.queryQuestionCount());
    }

    @GetMapping(value="/test/queryAllTables")
    public AjaxResult queryAllTables(){
        return AjaxResult.ok(testService.queryAllTables());
    }

    @GetMapping(value="/test/queryAllExperiments")
    public AjaxResult queryAllExperiments(){
        return AjaxResult.ok(testService.queryAllExperiments());
    }

    @GetMapping(value="/test/deleteTableById")
    public AjaxResult deleteTableById(String table_id){
        return testService.deleteTable(table_id)?AjaxResult.okMessage("删除成功"):AjaxResult.errorMsg("删除失败");
    }

    @GetMapping(value="/test/deleteExperimentById")
    public AjaxResult deleteExperimentById(String experiment_id){
        return testService.deleteExperiment(experiment_id)?AjaxResult.okMessage("删除成功"):AjaxResult.errorMsg("删除失败");
    }

    @PostMapping(value="/test/updateTest")
    public AjaxResult updateTest(@RequestBody TestVO testVO){
        return testService.updateTest(testVO)?AjaxResult.okMessage("更新成功"):AjaxResult.errorMsg("更新失败");
    }

    @PostMapping(value="/test/insertTestGroups")
    public AjaxResult insertTestGroups(@RequestBody TestGroupsVO testGroupsVO){
        return testService.insertTestGroups(testGroupsVO)?AjaxResult.okMessage("添加成功"):AjaxResult.errorMsg("添加失败");
    }

    @PostMapping(value="/test/insertTestGroup")
    public AjaxResult insertTestGroup(int user_id, int test_id){
        return testService.insertTestGroup(user_id, test_id)?AjaxResult.okMessage("添加成功"):AjaxResult.errorMsg("添加失败");
    }

    @PostMapping(value="/test/deleteTestGroup")
    public AjaxResult deleteTestGroup(int testGroupId){
        return testService.deleteTestGroup(testGroupId)?AjaxResult.okMessage("删除成功"):AjaxResult.errorMsg("删除失败");
    }

    @PostMapping(value="/test/queryTestGroupsByTestId")
    public AjaxResult queryTestGroupsByTestId(int test_id){
        return AjaxResult.ok(testService.queryTestGroupsByTestId(test_id));
    }

    @PostMapping(value="/test/queryTestListByUserId")
    public AjaxResult queryTestListByUserId(HttpSession session){
//        int user_id = 3;
        int user_id = (int)session.getAttribute("user_id");
        return AjaxResult.ok(testService.queryTestListByUserId(user_id));
    }

    @PostMapping(value="/test/queryTestById")
    public AjaxResult queryTestById(int test_id){
        return AjaxResult.ok(testService.queryTestById(test_id));
    }

    @PostMapping(value="/test/isQuestionExist")
    public AjaxResult isQuestionExist(String question_id){
        return testService.isQuestionExist(question_id)?AjaxResult.ok("存在这个题目"):AjaxResult.errorMsg("不存在这个题目");
    }

    @PostMapping(value="/test/queryTestGroupList")
    public AjaxResult queryTestGroupList(int index, int size){
        return AjaxResult.ok(testService.queryTestGroupsList(index, size));
    }

    @PostMapping(value="/test/queryTestGroupCount")
    public AjaxResult queryTestGroupCount(){
        return AjaxResult.ok(testService.queryTestGroupCount());
    }

    @PostMapping(value="/test/mohuQueryTableByTitle")
    public AjaxResult mohuQueryTableByTitle(String title){
        return AjaxResult.ok(testService.mohuQueryTableByTitle(title));
    }


}
