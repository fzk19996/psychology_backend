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
import com.example.buaadataplatform.module.service.AnswerService;
import com.netflix.discovery.converters.Auto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("answerService")
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerMapper answerMapper;

    @Autowired
    TableMapper tableMapper;

    @Autowired
    TestMapper testMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Resource
    RedisUtil redisUtil;

    @Override
    public List<AnswerVO> queryAnswerList(int index, int size) {
        List<AnswerEntity> answerEntityList = answerMapper.selectPage(new Page(index, size), null).getRecords();
        List<AnswerVO> answerVOList = new ArrayList<>();
        for(AnswerEntity answerEntity:answerEntityList){
            answerVOList.add(new AnswerVO(answerEntity));
        }
        return answerVOList;
    }

    @Override
    public boolean adminGetAnswer(int test_id, HttpServletResponse response) {
        try{
            TestEntity testEntity = testMapper.selectById(test_id);
            List<AnswerEntity> answerEntityList = answerMapper.selectList(new QueryWrapper<AnswerEntity>().eq("test_id", test_id));
            List<AnswerVO> answerVOList = new ArrayList<>();
            for(AnswerEntity answerEntity:answerEntityList){
                answerVOList.add(new AnswerVO(answerEntity));
            }
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet_table = workbook.createSheet("量表答案");
            XSSFSheet sheet_experiment = workbook.createSheet("实验答案");
            String filename = testEntity.getTitle()+"答案.xls";
            int row_table = 0;
            int row_experiment = 0;
            for(int i=0;i<answerVOList.size();i++) {
                AnswerVO answerVO = answerVOList.get(i);
                UserEntity userEntity = userMapper.selectById(answerVO.getUserId());
                if(userEntity==null){
                    continue;
                }
//                UserEntity userEntity = userMapper.selectById(3);
                List<AnswerQuestionVO> tableAnswerVOList = answerVO.getTable_answer_list();
                List<AnswerQuestionVO> experimentAnswerVOList = answerVO.getExperiment_answer_list();
                if (i == 0) {
                    XSSFRow table_head = sheet_table.createRow(row_table++);
                    table_head.createCell(0).setCellValue("答案id");
                    table_head.createCell(1).setCellValue("用户名");
                    for (int j = 0; j < tableAnswerVOList.size(); j++) {
                        table_head.createCell(j + 2).setCellValue(tableAnswerVOList.get(j).getQuestion_id());
                    }
                    XSSFRow experiment_head = sheet_experiment.createRow(row_experiment++);
                    experiment_head.createCell(0).setCellValue("答案id");
                    experiment_head.createCell(1).setCellValue("用户名");
                    for (int j = 0; j < experimentAnswerVOList.size(); j++) {
                        experiment_head.createCell(j * 3 + 2).setCellValue(experimentAnswerVOList.get(j).getQuestion_id() + ":用时");
                        experiment_head.createCell(j * 3 + 2 + 1).setCellValue(experimentAnswerVOList.get(j).getQuestion_id() + ":正确");
                        experiment_head.createCell(j * 3 + 2 + 2).setCellValue(experimentAnswerVOList.get(j).getQuestion_id() + ":答案");
                    }
                }
                XSSFRow table_row = sheet_table.createRow(row_table++);
                table_row.createCell(0).setCellValue(answerEntityList.get(i).getAnswerId());
                table_row.createCell(1).setCellValue(userEntity.getUsername());
                for(int j=0;j<tableAnswerVOList.size();j++){
                    if(tableAnswerVOList.get(j).getQue_type().equals("填空")){
                        table_row.createCell(j+2).setCellValue(tableAnswerVOList.get(j).getAnswer());
                    }else{
                        table_row.createCell(j+2).setCellValue(tableAnswerVOList.get(j).getScore());
                    }
                }
                XSSFRow experiment_row = sheet_experiment.createRow(row_experiment++);
                experiment_row.createCell(0).setCellValue(answerEntityList.get(i).getAnswerId());
                experiment_row.createCell(1).setCellValue(userEntity.getUsername());
                for(int j=0;j<experimentAnswerVOList.size();j++){
                    experiment_row.createCell(j*3+2).setCellValue(experimentAnswerVOList.get(j).getTime_use());
                    experiment_row.createCell(j*3+2+1).setCellValue(experimentAnswerVOList.get(j).getCorrect());
                    experiment_row.createCell(j*3+2+2).setCellValue(experimentAnswerVOList.get(j).getAnswer());
                }
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            response.flushBuffer();
            workbook.write(response.getOutputStream());

            response.flushBuffer();
//            File download_file = new File("文件在服务器上的路径");
//            //获取要下载文件的输入流，根据文件在服务器上的路径
//            FileInputStream fileInputStream = new FileInputStream(download_file);
//            //buf是临时缓存
//            byte[] buf = new byte[1024];
//            //将文件中的内容读到buf中，read为读到的内容的大小。
//            int read = fileInputStream.read(buf);
//            //返回给前端的输出流
//            ServletOutputStream outputStream = response.getOutputStream() ;
//            //read为-1了表示文件里的所有内容都读出来了
//            while(read!=-1){
//                //降临时缓存中的内容写到返回给前端的输出流中
//                outputStream.write(buf,0,buf.length);
//                outputStream.flush();
//                //因为文件大小会比临时缓存大小要大，所以没有读完还要继续读。
//                read = fileInputStream.read(buf);
//            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public QuestionVO queryQuestionInExam(String type, int index, HttpSession session) {
        int user_id = (Integer)session.getAttribute("user_id");
        String redis_key = "redis_answer_"+user_id;
        if(!redisUtil.hasKey(redis_key))
            return null;
        RedisAnswerVO redisAnswerVO = new RedisAnswerVO((RedisAnswerEntity)redisUtil.get(redis_key));
        if(type.equals("experiment")){
            return redisAnswerVO.getExperimentQuestionVOList().get(index);
        }
        else if(type.equals("table")){
            return redisAnswerVO.getTableQuestionVOList().get(index);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean addAnswer(AnswerQuestionVO answerQuestionVO, HttpSession session) {
        int user_id = (Integer) session.getAttribute("user_id");
        String redis_key = "redis_answer_"+user_id;
        if(!redisUtil.hasKey(redis_key))
            return false;
        RedisAnswerVO redisAnswerVO = new RedisAnswerVO((RedisAnswerEntity)redisUtil.get(redis_key));
        String type = answerQuestionVO.getType();
        int index = answerQuestionVO.getIndex();
        if(type.equals("experiment")){
            int q_index = answerQuestionVO.getIndex();
            List<AnswerQuestionVO> experimentAnswerList = redisAnswerVO.getExperimentAnswerList();
            experimentAnswerList.set(q_index, answerQuestionVO);
            redisAnswerVO.setExperimentAnswerList(experimentAnswerList);
            redisAnswerVO.setQuestion_index_e(q_index+1);
        }
        else if(type.equals("table")){
            int q_index = answerQuestionVO.getIndex();
            List<AnswerQuestionVO> tableAnswerList = redisAnswerVO.getTableAnswerList();
            tableAnswerList.set(q_index, answerQuestionVO);
            redisAnswerVO.setTableAnswerList(tableAnswerList);
            redisAnswerVO.setQuestion_index_t(q_index+1);
        }
        else{
            return false;
        }
        redisUtil.set(redis_key, new RedisAnswerEntity(redisAnswerVO));
        return true;
    }

    @Override
    public QuestionVO continueExam(HttpSession session) {
        int user_id = (Integer) session.getAttribute("user_id");
        String answer_reids_key = "redis_answer_"+user_id;
        if(!redisUtil.hasKey(answer_reids_key)){
            return null;
        }
        RedisAnswerVO redisAnswerVO = new RedisAnswerVO((RedisAnswerEntity)redisUtil.get(answer_reids_key));
        if(redisAnswerVO.getQuestion_index_t()>=redisAnswerVO.getTableQuestionVOList().size()){
            QuestionVO questionVO = redisAnswerVO.getExperimentQuestionVOList().get(redisAnswerVO.getQuestion_index_e());
            questionVO.setIndex(redisAnswerVO.getQuestion_index_e());
            return questionVO;
        }else{
            QuestionVO questionVO = redisAnswerVO.getTableQuestionVOList().get(redisAnswerVO.getQuestion_index_t());
            questionVO.setIndex(redisAnswerVO.getQuestion_index_t());
            return questionVO;
        }
    }

    @Override
    public boolean endExam(HttpSession session) {
        int user_id = (Integer) session.getAttribute("user_id");
        String answer_reids_key = "redis_answer_"+user_id;
        if(!redisUtil.hasKey(answer_reids_key)){
            return false;
        }
        redisUtil.del(answer_reids_key);
        return true;
    }

    @Override
    public RedisAnswerVO queryRedisAnswerVO(HttpSession session) {
        int user_id = (Integer) session.getAttribute("user_id");
        String answer_reids_key = "redis_answer_"+user_id;
        if(!redisUtil.hasKey(answer_reids_key)){
            return null;
        }
        RedisAnswerEntity redisAnswerEntity = (RedisAnswerEntity)redisUtil.get(answer_reids_key);
        RedisAnswerVO redisAnswerVO = new RedisAnswerVO(redisAnswerEntity);
        return redisAnswerVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean submitAnswer(HttpSession session) {
        int user_id = (Integer)session.getAttribute("user_id");
        String answer_reids_key = "redis_answer_"+user_id;
        if(!redisUtil.hasKey(answer_reids_key)){
            return false;
        }
        RedisAnswerEntity redisAnswerEntity = (RedisAnswerEntity)redisUtil.get(answer_reids_key);
        if(redisAnswerEntity.getUse_video()==1 && redisAnswerEntity.getVideoUrl()==null){
            return false;
        }
        RedisAnswerVO redisAnswerVO = new RedisAnswerVO(redisAnswerEntity);
        List<AnswerQuestionVO> tableAnswerList = redisAnswerVO.getTableAnswerList();
        List<AnswerQuestionVO> experimentAnswerList = redisAnswerVO.getExperimentAnswerList();

        Map<String, Integer> scoreMap = new HashMap<>();
        for(int i=0;i<tableAnswerList.size();i++){
            if(tableAnswerList.get(i).getQue_type().equals("单选")||tableAnswerList.get(i).getQue_type().equals("多选")){
                scoreMap.put(tableAnswerList.get(i).getQuestion_id(),tableAnswerList.get(i).getScore());
            }
        }
        TestEntity testEntity = testMapper.selectById(redisAnswerVO.getTest_id());
        if(testEntity==null)
            return false;
        TestVO testVO = new TestVO(testEntity);
        List<varVO> vars = testVO.getVarList();
//        String[] res_name_list = testEntity.getVarList().split(";");
//        String[] expression_list  = testEntity.getExpressionList().split(";");
        List<String> score_list = new ArrayList<>();
        List<String> var_name_list = new ArrayList<>();
        for(int i=0;i<vars.size();i++){
            String expression = vars.get(i).getExpression();
            String varName = vars.get(i).getVarName();
            score_list.add(CommonUitls.calc_score(expression, scoreMap)+"");
            var_name_list.add(varName);
        }
        AnswerVO answerVO = new AnswerVO();
        answerVO.setTable_answer_list(tableAnswerList);
        answerVO.setExperiment_answer_list(experimentAnswerList);
        answerVO.setScoreList(score_list);
        answerVO.setVideoUrl(redisAnswerEntity.getVideoUrl());
        answerVO.setTestId(redisAnswerVO.getTest_id());
        answerVO.setUserId(user_id);
        AnswerEntity answerEntity = new AnswerEntity(answerVO);
        if(answerMapper.insert(answerEntity)==1){
            redisUtil.del(answer_reids_key);
            return true;
        }else{
            return false;
        }
//        return answerMapper.insert(answerEntity)==1?true:false;
    }

    @Override
    public ResultVO queryAnswerResult(int answer_id) {
        AnswerEntity answerEntity = answerMapper.selectById(answer_id);
        if(answerEntity==null)
            return null;
        AnswerVO answerVO = new AnswerVO(answerEntity);
        TestEntity testEntity = testMapper.selectById(answerVO.getTestId());
        if(testEntity==null)
            return null;
        TestVO testVO = new TestVO(testEntity);
        List<varVO> varVOS = testVO.getVarList();
        List<String> commentList = new ArrayList<>();
        List<String> scoreList = answerVO.getScoreList();
        List<String> varNameList = new ArrayList<>();
        for(int i=0;i<varVOS.size();i++){
            varVO var = varVOS.get(i);
            varNameList.add(var.getVarName());
            List<Float> score_stages = var.getScores();
            float score = Float.parseFloat(scoreList.get(i));
            for(int j=0;j<score_stages.size();j++){
                if(score<=score_stages.get(j)){
                    commentList.add(var.getComments().get(j));
                    break;
                }
                if(j==score_stages.size()-1) {
                    commentList.add("分数不在档位之内");
                    break;
                }
            }
        }
        ResultVO resultVO = new ResultVO();
        resultVO.setAdminComment(answerVO.getComment());
        resultVO.setScoreList(scoreList);
        resultVO.setCommentList(commentList);
        resultVO.setVarNameList(varNameList);
        resultVO.setTest_name(testVO.getTitle());
        return resultVO;
    }

    @Override
    public List<AnswerEntity> queryUserAnswers(int user_id) {
        List<AnswerEntity> answerEntityList = answerMapper.selectList(new QueryWrapper<AnswerEntity>().select("answer_id", "create_time", "state").eq("user_id", user_id));
        return answerEntityList;
    }

    @Override
    public boolean deleteAnswer(int answer_id) {
        return answerMapper.deleteById(answer_id)==1?true:false;
    }

    @Override
    public boolean updateAnswer(AnswerEntity answerEntity) {
        return answerMapper.updateById(answerEntity)==1?true:false;
    }

}
