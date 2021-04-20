package com.example.buaadataplatform.module.service;

import com.example.buaadataplatform.module.VO.*;
import com.example.buaadataplatform.module.entity.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface TestService {

    public List<TestEntity> queryTestList(int offset, int limit);

    public boolean deleteTest(int test_id);

    public int beginExam(int test_id, HttpSession session);

    public boolean addTest(TestVO testVO);

    public List<TestEntity> queryTestByTitle(String title);

    public boolean addTable(TableEntity tableEntity);

    public boolean addExperiment(ExperimentEntity experimentEntity);

    public List<TableEntity> queryTableByTtile(String title);

    public List<ExperimentEntity> queryExperimentByTitle(String title);

    public List<TableEntity> queryTablesByIdList(String table_id_list);

    public boolean updateAnswer(AnswerEntity answerEntity);

    public TableVO queryTableVOById(int table_id);

    public ExperimentVO queryExperimentVOById(int experiment_id);

    public boolean updateExperiment(ExperimentEntity experimentEntity);

    public boolean updateTable(TableEntity tableEntity);

    public boolean addQuestion(QuestionVO questionVO);

    public List<QuestionVO> queryQuestionList(int index, int size);

    public List<QuestionVO> mohuQueryQuestion(String question_id);

    public List<TestEntity> mohuQueryTest(String title);

    public boolean deleteQuestion(String question_id);

    public QuestionVO queryQuestionVOById(String question_id);

    public boolean updateQuetion(QuestionVO questionVO);

    public int queryQuestionCount();

    public List<TableEntity> queryAllTables();

    public boolean deleteTable(String table_id);

    public List<ExperimentEntity> queryAllExperiments();

    public boolean deleteExperiment(String experiment_id);

    public boolean updateTest(TestVO testVO);

    public List<TestEntity> queryTestListByUserId(int user_id);

    public boolean insertTestGroup(int user_id, int test_id);

    public boolean insertTestGroups(TestGroupsVO testGroupsVO);

    public boolean deleteTestGroup(int test_group_id);

    public List<TestGroupEntity> queryTestGroupsByTestId(int test_id);

    public List<TestGroupEntity> queryTestGroupsList(int index, int size);

    public int queryTestGroupCount();

    public TestVO queryTestById(int test_id);

    public boolean isQuestionExist(String question_id);

    public List<TableEntity> mohuQueryTableByTitle(String title);

}
