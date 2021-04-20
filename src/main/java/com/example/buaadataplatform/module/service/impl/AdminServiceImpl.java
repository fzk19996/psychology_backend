package com.example.buaadataplatform.module.service.impl;

import com.example.buaadataplatform.module.constant.Authority;
import com.example.buaadataplatform.module.entity.AdminEntity;
import com.example.buaadataplatform.module.mapper.xinli.AdminMapper;
import com.example.buaadataplatform.module.mapper.xinli.UserMapper;
import com.example.buaadataplatform.module.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean adminLogin(String adminName, String password, HttpSession session) {
        AdminEntity adminEntity = adminMapper.queryAdminInfoByAdminName(adminName, password);
        if(adminEntity!=null){
            session.setAttribute("authority", Authority.Admin);
            session.setAttribute("user_id", adminEntity.getAdmin_id());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean adminSignOut(HttpSession session) {
        session.invalidate();
        return true;
    }

    @Override
    public boolean findAdminPassword(String username, String oldPassword, String newPassword, String confirmPassword, String captcha_code, HttpSession httpSession) {
        if (!oldPassword.equals(newPassword))
            return false;
        if((int)httpSession.getAttribute("authority")==Authority.Admin){
            if(adminMapper.updateAdminPassword(newPassword, oldPassword, (int)httpSession.getAttribute("id"), username )<=0){
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    @Override
    public AdminEntity adminInfo(HttpSession session) {
        int adminId = (int)session.getAttribute("id");
        return adminMapper.queryAdminInfoByAdminId(adminId);
    }

    @Override
    public boolean addAdmin(AdminEntity adminEntity) {
        if(adminMapper.insertAdmin(adminEntity)==0)
            return false;
        else
            return true;
    }

    @Override
    public boolean updateAdmin(String password, String admin, HttpSession session) {
        int adminId = (int)session.getAttribute("id");
        if(adminMapper.updateAdmin(password, admin, adminId)==0)
            return false;
        else
            return true;
    }

    @Override
    public List<AdminEntity> getAdminAll(int limit, int offset) {
        List<AdminEntity> adminList = adminMapper.queryAdminInfoAll();
        if(offset>=adminList.size()){
            return new ArrayList<>();
        }else{
            return adminList.subList(offset, (offset+limit)<adminList.size()?(offset+limit-1):(adminList.size()));
        }
    }

    @Override
    public boolean delAdmin(int adminId) {
        if(adminMapper.delAdminById(adminId)==0)
            return false;
        else
            return true;
    }

}