package com.example.buaadataplatform.module.mapper.xinli;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.buaadataplatform.module.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper extends BaseMapper<TestEntity> {

    public List<TestEntity> queryTestList();

    public int delTestById(int test_id);

    public TestEntity queryTestById(int test_id);

    public int addAnswer(AnswerEntity answerEntity);

    public int addDiary(DiaryEntity diaryEntity);

    public DiaryEntity queryDiaryById(int diary_id);

    public int insertTest(TestEntity testEntity);

    public int addQuestion(QuestionEntity questionEntity);

    public int insertOption(OptionEntity optionEntity);

    public List<QuestionEntity> queryQuestionByTestId(int test_id);

    public List<OptionEntity> queryOptionByQuestionId(int question_id);

    public List<TestEntity> queryTestByTitle(String title);

    public int insertTable(TableEntity tableEntity);

    public int insertExperiment(ExperimentEntity experimentEntity);

    public List<TableEntity> queryTableByTitle(String title);

    public List<ExperimentEntity> queryExperimentByTitle(String title);

    public List<QuestionEntity> queryQuestionByTableId(int table_id);

    public List<QuestionEntity> queryQuestionByExperimentId(int experiment_id);

    public TableEntity queryTableById(int table_id);

    public ExperimentEntity queryExperimentById(int experiment_id);

    public List<AnswerEntity> queryAnswerList(int index, int size);

    public int updateAnswer(AnswerEntity answerEntity);

    public int updateExperiment(ExperimentEntity experimentEntity);

    public int updateTable(TableEntity tableEntity);

    public int updateTest(TestEntity testEntity);

    public QuestionEntity queryQuestionById(String question_id);

    public List<AnswerEntity> queryAnswerByTestId(int test_id);

    public List<QuestionEntity> queryQuestionsByIdList(String[] ids);

    public List<QuestionEntity> queryQuestionList(int index, int size);

    public List<QuestionEntity> mohuQueryQuestion(String question_id);

    public int deleteQuestion(String question_id);

    public int updateQuestion(QuestionEntity questionEntity);

    public int queryQuestionCount();

    public List<TableEntity> queryAllTable();

    public List<ExperimentEntity> queryAllExperiment();

    public int delTableById(String table_id);

    public int delExperimentById(String experiment_id);

    public int delAnswer(int test_id);



}
