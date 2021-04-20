package com.example.buaadataplatform.module.service;

import com.example.buaadataplatform.module.entity.ArticleEntity;
import com.example.buaadataplatform.module.entity.PushEntity;

import java.util.List;

public interface PushService {

    public List<PushEntity> queryUserPushs(int user_id);

    public List<PushEntity> queryPushList(int index, int size);

    public boolean updatePush(PushEntity pushEntity);

    public boolean insertPush(PushEntity pushEntity);

    public boolean deletePush(int push_id);

    public PushEntity queryPushById(int push_id);

    public int queryCntPush();

}
