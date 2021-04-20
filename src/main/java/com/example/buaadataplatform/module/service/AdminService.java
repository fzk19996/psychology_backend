package com.example.buaadataplatform.module.service;

import com.example.buaadataplatform.module.entity.AdminEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AdminService {
    public boolean adminLogin(String adminName, String password, HttpSession session);

    public boolean adminSignOut(HttpSession session);

    public boolean findAdminPassword(String username, String oldPassword, String newPassword, String confirmPassword, String captcha_code, HttpSession httpSession);

    public AdminEntity adminInfo(HttpSession session);

    public boolean addAdmin(AdminEntity adminEntity);

    public boolean updateAdmin(String password, String admin, HttpSession session);

    public List<AdminEntity> getAdminAll(int limit, int offset);

    public boolean delAdmin(int adminId);
}
