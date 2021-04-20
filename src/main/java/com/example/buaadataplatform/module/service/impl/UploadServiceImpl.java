package com.example.buaadataplatform.module.service.impl;

import com.example.buaadataplatform.common.utils.RedisUtil;
import com.example.buaadataplatform.module.VO.RedisAnswerVO;
import com.example.buaadataplatform.module.entity.RedisAnswerEntity;
import com.example.buaadataplatform.module.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    @Value("${static.root.path}")
    String staticPath;

    @Resource
    RedisUtil redisUtil;

    @Override
    public String imgUpload(MultipartFile file) {
        String img_path = staticPath+"/img/";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy_MM_dd");
        String date_now = formatter.format(new Date(System.currentTimeMillis()));
        img_path += date_now;
        File img_dir_file = new File(img_path);
        int img_index;
        if(!img_dir_file.exists()){
            img_dir_file.mkdirs();
            img_index = 1;
        }else{
            img_index = img_dir_file.listFiles().length+1;
        }
        String suffname = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        String dest_path = img_path+'/'+img_index+suffname;
        try {
            file.transferTo(new File(dest_path));
            return "/static/img/"+date_now+'/'+img_index+suffname;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String videoUpload(MultipartFile file, HttpSession session) {
        int user_id = (Integer)session.getAttribute("user_id");
        String answer_reids_key = "redis_answer_"+user_id;
        if(!redisUtil.hasKey(answer_reids_key)){
            return null;
        }
        RedisAnswerEntity redisAnswerEntity = (RedisAnswerEntity)redisUtil.get(answer_reids_key);
        RedisAnswerVO redisAnswerVO = new RedisAnswerVO(redisAnswerEntity);
        String video_path = staticPath+"/video/";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy_MM_dd");
        String date_now = formatter.format(new Date(System.currentTimeMillis()));
        video_path += date_now;
        File video_dir_file = new File(video_path);
        int video_index;
        if(!video_dir_file.exists()){
            video_dir_file.mkdirs();
            video_index = 1;
        }else{
            video_index = video_dir_file.listFiles().length+1;
        }
        String suffname = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        String dest_path = video_path+'/'+video_index+suffname;
        try {
            file.transferTo(new File(dest_path));
            redisAnswerEntity.setVideoUrl("/static/video/"+date_now+'/'+video_index+suffname);
            redisUtil.set(answer_reids_key, redisAnswerEntity);
            return "/static/video/"+date_now+'/'+video_index+suffname;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
