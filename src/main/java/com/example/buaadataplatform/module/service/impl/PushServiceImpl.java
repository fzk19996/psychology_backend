package com.example.buaadataplatform.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.buaadataplatform.module.constant.ArticleType;
import com.example.buaadataplatform.module.entity.ArticleEntity;
import com.example.buaadataplatform.module.entity.PushEntity;
import com.example.buaadataplatform.module.mapper.xinli.ArticleMapper;
import com.example.buaadataplatform.module.mapper.xinli.PushMapper;
import com.example.buaadataplatform.module.service.PushService;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("pushService")
public class PushServiceImpl implements PushService {

    @Autowired
    PushMapper pushMapper;

    @Autowired
    ArticleMapper articleMapper;


    @Override
    public List<PushEntity> queryUserPushs(int user_id) {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String date_now = sdf2.format(new Date());
        List<PushEntity> pushEntities = pushMapper.selectList(new QueryWrapper<PushEntity>().like("user_ids", ";"+user_id+";").
                apply(true,
                "date_format (start_date,'%Y-%m-%d') <= date_format('" +date_now + "','%Y-%m-%d')")
                .apply(true,
                        "date_format (end_date,'%Y-%m-%d') >= date_format('" +date_now+ "','%Y-%m-%d')"));
        return pushEntities;
    }

    @Override
    public List<PushEntity> queryPushList(int index, int size) {
        return pushMapper.selectPage(new Page(index,size), null).getRecords();
    }

    @Override
    public boolean updatePush(PushEntity pushEntity) {
        return pushMapper.updateById(pushEntity)==1?true:false;
    }

    @Override
    public boolean insertPush(PushEntity pushEntity) {
        return pushMapper.insert(pushEntity)==1?true:false;
    }

    @Override
    public boolean deletePush(int push_id) {
        return pushMapper.deleteById(push_id)==1?true:false;
    }

    @Override
    public PushEntity queryPushById(int push_id) {
        return pushMapper.selectById(push_id);
    }

    @Override
    public int queryCntPush() {
        return pushMapper.selectCount(null);
    }
}
