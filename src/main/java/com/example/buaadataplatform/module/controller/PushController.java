package com.example.buaadataplatform.module.controller;

import com.example.buaadataplatform.common.VO.AjaxResult;
import com.example.buaadataplatform.module.entity.ArticleEntity;
import com.example.buaadataplatform.module.entity.PushEntity;
import com.example.buaadataplatform.module.service.PushService;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class PushController {

    @Autowired
    PushService pushService;

    @PostMapping("/push/queryUserPushs")
    public AjaxResult queryUserPushs(HttpSession session){
        int user_id = (int)session.getAttribute("user_id");
//        int user_id = 3;
        return AjaxResult.ok(pushService.queryUserPushs(user_id))
                ;
    }

    @PostMapping("/push/queryPushList")
    public AjaxResult queryPushList(int index, int size){
        return AjaxResult.ok(pushService.queryPushList(index, size));
    }

    @PostMapping("/push/insertPush")
    public AjaxResult insertPush(@RequestBody PushEntity pushEntity){
        return pushService.insertPush(pushEntity)?AjaxResult.ok():AjaxResult.errorMsg("添加推送失败");
    }

    @PostMapping("/push/deletePush")
    public AjaxResult delPush(int pushId){
        return pushService.deletePush(pushId)?AjaxResult.ok():AjaxResult.errorMsg("删除推送失败");
    }

    @PostMapping("/push/updatePush")
    public AjaxResult updatePush(@RequestBody PushEntity pushEntity){
        return pushService.updatePush(pushEntity)?AjaxResult.ok():AjaxResult.errorMsg("更新推送失败");
    }

    @PostMapping("/push/queryPushCount")
    public AjaxResult queryPushCount(){
        return AjaxResult.ok(pushService.queryCntPush());
    }

    @PostMapping("/push/queryPushById")
    public AjaxResult queryPushById(int push_id){
        return AjaxResult.ok(pushService.queryPushById(push_id));
    }


}
