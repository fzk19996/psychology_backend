package com.example.buaadataplatform.module.controller;


import com.example.buaadataplatform.common.VO.AjaxResult;
import com.example.buaadataplatform.module.entity.UserEntity;
import com.example.buaadataplatform.module.service.UserService;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/v1/user/list")
    public AjaxResult queryUserList(int limit, int offset){
        return AjaxResult.ok(userService.queryUserList(offset, limit));
    }

    @RequestMapping(value="/user/queryUserById", method= RequestMethod.POST)
    public AjaxResult queryUserById(int user_id){
        return AjaxResult.ok(userService.queryUserById(user_id));
    }

    @RequestMapping(value="/user/info", method=RequestMethod.GET)
    public AjaxResult queryUserInfo(HttpSession session){
        int id = (Integer) (session.getAttribute("user_id"));
        UserEntity tmp = userService.queryUserById(id);
        return tmp!=null?AjaxResult.ok(tmp):AjaxResult.errorMsg("查询信息失败");
    }

    @PostMapping("/user/updateuser")
    public AjaxResult updateUser(@RequestBody UserEntity userEntity){
        return userService.updateUser(userEntity)?AjaxResult.okMessage("更新信息成功"):AjaxResult.errorMsg("更新信息失败");
    }

    @RequestMapping(value="/user/deleteUser", method= RequestMethod.POST)
    public AjaxResult deleteUserById(int user_id){
        return userService.delteUser(user_id)?AjaxResult.okMessage("删除用户成功"):AjaxResult.errorMsg("删除用户失败");
    }

    @GetMapping("/v1/user/age/count")
    public AjaxResult queryUserAgeCount(){
        return AjaxResult.ok(userService.ageCount());
    }

    @RequestMapping(value="/user/login", method = RequestMethod.POST)
    public AjaxResult userLogin( @RequestBody  UserEntity userEntity, HttpSession session){
        return userService.login(userEntity, session)?AjaxResult.okMessage("登陆成功"):AjaxResult.errorMsg("登陆失败");
    }

    @GetMapping("/user/logout")
    public AjaxResult userLogout(HttpSession session){
        return userService.logout(session)?AjaxResult.okMessage("退出成功"):AjaxResult.errorMsg("退出失败");
    }

    @RequestMapping(value="/user/addUser", method = RequestMethod.POST)
    public AjaxResult addUser(@RequestBody UserEntity userEntity){
        return userService.insertUser(userEntity)? AjaxResult.okMessage("添加用户成功"):AjaxResult.errorMsg("添加用户失败");
    }

    @RequestMapping(value="/user/mohuQueryUser", method = RequestMethod.POST)
    public AjaxResult mohuQueryUser(String username){
        return AjaxResult.ok(userService.mohuQueryUser(username));
    }

    @RequestMapping(value="/user/updatePassword", method = RequestMethod.POST)
    public AjaxResult updatePassword(String password, HttpSession session){
        return userService.updatePassword(password, session)?AjaxResult.ok("更新密码成功"):AjaxResult.errorMsg("更新密码失败");
    }



}
