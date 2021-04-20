package com.example.buaadataplatform.module.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.buaadataplatform.common.utils.CommonUitls;
import com.example.buaadataplatform.common.utils.RedisUtil;
import com.example.buaadataplatform.module.VO.*;
import com.example.buaadataplatform.module.entity.*;
import com.example.buaadataplatform.module.mapper.xinli.*;
import com.example.buaadataplatform.module.service.TestService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.Null;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.events.Comment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("testSerivce")
public class TestServiceImpl implements TestService {

    @Autowired
    TestMapper testMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TableMapper tableMapper;

    @Autowired
    ExperimentMapper experimentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    TestGroupMapper testGroupMapper;

    @Resource
    RedisUtil redisUtil;

    @Value("${static.root.path}")
    String staticPath;

    @Override
    public List<TestEntity> queryTestList(int offset, int limit) {
        return testMapper.selectPage(new Page<TestEntity>(offset, limit),null).getRecords();
    }

    @Override
    public boolean deleteTest(int test_id) {
        testGroupMapper.delete(new QueryWrapper<TestGroupEntity>().eq("test_id", test_id));
        return testMapper.deleteById(test_id)==1?true:false;
    }

    @Override
    public int beginExam(int test_id, HttpSession session){
        try {
            int user_id = (Integer) session.getAttribute("user_id");
            String redis_key = "redis_answer_"+user_id;
            if(redisUtil.hasKey(redis_key)){
                return 2;
            }
            TestEntity testEntity = testMapper.selectById(test_id);
            List<TableVO> tableVOList = new ArrayList<>();
            String[] tableIds = testEntity.getTableId().split(";");
            int question_table_cnt = 0;
            List<QuestionVO> tableQuestionVOList = new ArrayList<>();
            for (int i = 0; i < tableIds.length; i++) {
                TableVO tableVO = queryTableVOById(Integer.parseInt(tableIds[i]));
                if (tableVO == null)
                    return -1;
                tableQuestionVOList.addAll(tableVO.getQuestions());
                question_table_cnt += tableVO.getQuestions().size();
            }
            ExperimentVO experimentVO = queryExperimentVOById(testEntity.getExperimentId());
            if (experimentVO == null)
                return -1;
            RedisAnswerVO redisAnswerVO = new RedisAnswerVO();
            redisAnswerVO.setTableQuestionVOList(tableQuestionVOList);
            redisAnswerVO.setExperimentQuestionVOList(experimentVO.getQuestions());
            redisAnswerVO.setTableAnswerList(Collections.nCopies(question_table_cnt, new AnswerQuestionVO()));
            redisAnswerVO.setExperimentAnswerList(Collections.nCopies(experimentVO.getQuestions().size(),  new AnswerQuestionVO()));
            redisAnswerVO.setUse_video(testEntity.getUseVideo());
            redisAnswerVO.setQuestion_index_e(0);
            redisAnswerVO.setQuestion_index_e(0);
            redisAnswerVO.setTest_id(test_id);
            redisUtil.set(redis_key, new RedisAnswerEntity(redisAnswerVO));
            redisUtil.expire(redis_key, 3600);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public boolean addTest(TestVO testVO) {
        TestEntity testEntity = new TestEntity(testVO);
        return testMapper.insert(testEntity)==1?true:false;
    }

    @Override
    public List<TestEntity> queryTestByTitle(String title) {
        return testMapper.selectList(new QueryWrapper<TestEntity>().eq("title",title));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addTable(TableEntity tableEntity) {
        return tableMapper.insert(tableEntity)==1?true:false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addExperiment(ExperimentEntity experimentEntity) {
        return experimentMapper.insert(experimentEntity)==1?true:false;
    }

    @Override
    public List<TableEntity> queryTableByTtile(String title) {
        return tableMapper.selectList(new QueryWrapper<TableEntity>().eq("title", title));
    }

    @Override
    public List<ExperimentEntity> queryExperimentByTitle(String title) {
        return experimentMapper.selectList(new QueryWrapper<ExperimentEntity>().eq("title", title));
    }

    @Override
    public List<TableEntity> queryTablesByIdList(String table_id_list) {
        return tableMapper.selectList(new QueryWrapper<TableEntity>().in("table_id", table_id_list.split(";")));
    }

    @Override
    public boolean updateAnswer(AnswerEntity answerEntity) {
        return testMapper.updateAnswer(answerEntity)==1?true:false;
    }

    @Override
    public TableVO queryTableVOById(int table_id) {
        TableEntity tableEntity = tableMapper.selectById(table_id);
        if(tableEntity==null)
            return null;
        List<QuestionEntity> questionEntityList = testMapper.queryQuestionsByIdList(tableEntity.getQuestionIdList().split(";"));
//        List<QuestionEntity> questionEntityList = questionMapper.selectList(
//                new QueryWrapper<QuestionEntity>().in("question_id", tableEntity.getQuestionIdList().split(";")));
//        if(questionEntityList.size()!=tableEntity.getQuestionIdList().split(";").length)
//            return null;
        List<QuestionVO> questionVOList = new ArrayList<>();
        for(QuestionEntity questionEntity:questionEntityList){
            questionVOList.add(new QuestionVO(questionEntity));
        }
        return new TableVO(tableEntity, questionVOList);
    }

    @Override
    public ExperimentVO queryExperimentVOById(int experiment_id) {
        ExperimentEntity experimentEntity= experimentMapper.selectById(experiment_id);
        if(experimentEntity==null)
            return null;
        List<QuestionEntity> questionEntityList = testMapper.queryQuestionsByIdList(experimentEntity.getQuestionIdList().split(";"));
//        List<QuestionEntity> questionEntityList = questionMapper.selectList(
//                new QueryWrapper<QuestionEntity>().in("question_id", experimentEntity.getQuestionIdList().split(";")));
//        if(questionEntityList.size()!=experimentEntity.getQuestionIdList().split(";").length)
//            return null;
        List<QuestionVO> questionVOList = new ArrayList<>();
        for(QuestionEntity questionEntity:questionEntityList){
            questionVOList.add(new QuestionVO(questionEntity));
        }
        return new ExperimentVO(experimentEntity, questionVOList);
    }

    @Override
    public boolean updateExperiment(ExperimentEntity experimentEntity) {
        return experimentMapper.updateById(experimentEntity)==1?true:false;
    }

    @Override
    public boolean updateTable(TableEntity tableEntity) {
        return tableMapper.updateById(tableEntity)==1?true:false;
    }

    @Override
    public boolean addQuestion(QuestionVO questionVO) {
        return questionMapper.insert(new QuestionEntity(questionVO))==1?true:false;
    }

    @Override
    public List<QuestionVO> queryQuestionList(int index, int size) {
        List<QuestionEntity> questionEntityList = questionMapper.selectPage(
                new Page<QuestionEntity>(index,size),null).getRecords();
        List<QuestionVO> questionVOList = new ArrayList<>();
        for(QuestionEntity questionEntity:questionEntityList){
            questionVOList.add(new QuestionVO(questionEntity));
        }
        return questionVOList;
    }

    @Override
    public List<QuestionVO> mohuQueryQuestion(String question_id) {
        List<QuestionEntity> questionEntityList = questionMapper.selectList(
                new QueryWrapper<QuestionEntity>().like("question_id", question_id));
        List<QuestionVO> questionVOList = new ArrayList<>();
        for(QuestionEntity questionEntity:questionEntityList){
            questionVOList.add(new QuestionVO(questionEntity));
        }
        return questionVOList;
    }

    @Override
    public List<TestEntity> mohuQueryTest(String title) {
        return testMapper.selectList(new QueryWrapper<TestEntity>().select("test_id", "title").like("title", title));
    }

    @Override
    public boolean deleteQuestion(String question_id) {
        return questionMapper.deleteById(question_id)==1?true:false;
    }

    @Override
    public QuestionVO queryQuestionVOById(String question_id) {
        QuestionEntity questionEntity = questionMapper.selectById(question_id);
        return new QuestionVO(questionEntity);
    }

    @Override
    public boolean updateQuetion(QuestionVO questionVO) {
        return questionMapper.updateById(new QuestionEntity(questionVO))==1?true:false;
    }

    @Override
    public int queryQuestionCount() {
        return  questionMapper.selectCount(null);
    }

    @Override
    public List<TableEntity> queryAllTables() {
        return tableMapper.selectList(null);
    }

    @Override
    public boolean deleteTable(String table_id) {
        return tableMapper.deleteById(table_id)==1?true:false;
    }

    @Override
    public List<ExperimentEntity> queryAllExperiments() {
        return experimentMapper.selectList(null);
    }

    @Override
    public boolean deleteExperiment(String experiment_id) {
        return experimentMapper.deleteById(experiment_id)==1?true:false;
    }

    @Override
    @Transactional
    public boolean updateTest(TestVO testVO) {
        TestEntity testEntity = new TestEntity(testVO);
        return testMapper.updateById(testEntity)==1?true:false;
    }

    @Override
    public List<TestEntity> queryTestListByUserId(int user_id) {
        List<TestGroupEntity> testGroupEntities = testGroupMapper.selectList(new QueryWrapper<TestGroupEntity>().eq("user_id", user_id));
        List<TestEntity> testEntityList = new ArrayList<>();
        for(TestGroupEntity t:testGroupEntities){
            TestEntity testEntity = testMapper.selectById(t.getTestId());
            if(testEntity!=null) {
                testEntityList.add(testEntity);
            }
        }
        return testEntityList;
    }

    @Override
    public boolean insertTestGroup(int user_id, int test_id) {
        return testGroupMapper.insert(new TestGroupEntity(test_id, user_id))==1?true:false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertTestGroups(TestGroupsVO testGroupsVO) {
        try{
            for (int i=0;i<testGroupsVO.getUser_ids().size();i++){
                if(testGroupMapper.insert(new TestGroupEntity(testGroupsVO.getTest_id(), testGroupsVO.getUser_ids().get(i)))==1){
                    throw new Exception();
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteTestGroup(int test_group_id) {
        return testGroupMapper.deleteById(test_group_id)==1?true:false;
    }

    @Override
    public List<TestGroupEntity> queryTestGroupsByTestId(int test_id) {
        return testGroupMapper.selectList(new QueryWrapper<TestGroupEntity>().eq("test_id",test_id));
    }

    @Override
    public List<TestGroupEntity> queryTestGroupsList(int index, int size) {
        return testGroupMapper.selectPage(new Page<TestGroupEntity>(index, size),null).getRecords();
    }

    @Override
    public int queryTestGroupCount() {
        return testGroupMapper.selectCount(null);
    }

    @Override
    public TestVO queryTestById(int test_id) {
        return new TestVO(testMapper.selectById(test_id));
    }

    @Override
    public boolean isQuestionExist(String question_id) {
        return questionMapper.selectById(question_id)==null?false:true;
    }

    @Override
    public List<TableEntity> mohuQueryTableByTitle(String title) {
        return tableMapper.selectList(new QueryWrapper<TableEntity>().select("table_id", "title").like("title", title));
    }

}
