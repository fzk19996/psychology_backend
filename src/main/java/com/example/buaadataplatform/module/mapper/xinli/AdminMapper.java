package com.example.buaadataplatform.module.mapper.xinli;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.buaadataplatform.module.entity.AdminEntity;

import java.util.List;

public interface AdminMapper extends BaseMapper<AdminEntity> {

    public AdminEntity queryAdminInfoByAdminName(String param1, String param2);

    public int updateAdminPassword(String newPassword, String oldPassword, int admin_id, String admin_name);

    public AdminEntity queryAdminInfoByAdminId(int id);

    public int insertAdmin(AdminEntity adminEntity);

    public int updateAdmin(String password, String admin, int admin_id);

    public List<AdminEntity> queryAdminInfoAll();

    public int delAdminById(int admin_id);
}