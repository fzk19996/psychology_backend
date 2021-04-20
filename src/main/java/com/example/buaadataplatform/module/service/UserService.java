package com.example.buaadataplatform.module.service;

import com.example.buaadataplatform.module.VO.UserAgeVO;
import com.example.buaadataplatform.module.entity.AdminEntity;
import com.example.buaadataplatform.module.entity.UserEntity;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {

    public List<UserEntity> queryUserList(int offset, int limit);

    public UserEntity queryUserById(int user_id);

    public boolean updateUser(UserEntity userEntity);

    public boolean delteUser(int userId);

    public UserAgeVO ageCount();

    public boolean login(UserEntity userEntity, HttpSession session);

    public boolean logout(HttpSession session);

    public boolean insertUser(UserEntity userEntity);

    public List<UserEntity> mohuQueryUser(String user_name);

    public boolean updatePassword(String password, HttpSession session);
}
