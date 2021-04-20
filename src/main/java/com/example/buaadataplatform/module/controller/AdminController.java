package com.example.buaadataplatform.module.controller;


import com.example.buaadataplatform.common.VO.AjaxResult;
import com.example.buaadataplatform.module.entity.AdminEntity;
import com.example.buaadataplatform.module.service.AdminService;
import com.netflix.ribbon.proxy.annotation.Http;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("login")
    public AjaxResult loginAuth(@RequestBody AdminEntity adminEntity, HttpSession session){
        if(adminService.adminLogin(adminEntity.getAdmin_name(), adminEntity.getPassword(), session))
            return AjaxResult.okMessage("登录成功");
        else
            return AjaxResult.errorMsg("登陆失败");
    }

    @GetMapping("signOut")
    public AjaxResult signOut(HttpSession session){
        return adminService.adminSignOut(session)?AjaxResult.okMessage("退出成功"):AjaxResult.errorMsg("退出失败");
    }

    @GetMapping("findPassword")
    public AjaxResult findPassword(String username, String oldPassword, String newPassword, String confirmPassword, String captchaCode, HttpSession session){
        return adminService.findAdminPassword(username,oldPassword, newPassword, confirmPassword, captchaCode,session )?AjaxResult.okMessage("密码修改成功"):AjaxResult.errorMsg("密码修改失败");
    }

    @PostMapping("info")
    public AjaxResult adminInfo(HttpSession session){
        return AjaxResult.ok(adminService.adminInfo(session));
    }

    @PostMapping("addAdmin")
    public AjaxResult addAdmin(@RequestBody AdminEntity adminEntity){
        return adminService.addAdmin(adminEntity)?AjaxResult.okMessage("添加管理员成功"):AjaxResult.errorMsg("添加管理员失败");
    }

    @PostMapping("updateAdmin")
    public AjaxResult updateAdmin(String password, String admin, HttpSession session){
        return adminService.updateAdmin(password, admin, session)?AjaxResult.okMessage("更新信息成功"):AjaxResult.errorMsg("更新信息失败");
    }

    @GetMapping("deleteAdmin")
    public AjaxResult deleteAdmin(int adminId){
        return adminService.delAdmin(adminId)?AjaxResult.okMessage("删除管理员成功"):AjaxResult.errorMsg("删除管理员失败");
    }

    @GetMapping("all")
    public AjaxResult queryAllAdmin(int offset, int limit){
        return AjaxResult.ok(adminService.getAdminAll(limit, offset));
    }






}
