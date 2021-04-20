package com.example.buaadataplatform.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.buaadataplatform.module.VO.UserAgeVO;
import com.example.buaadataplatform.module.constant.Authority;
import com.example.buaadataplatform.module.entity.UserEntity;
import com.example.buaadataplatform.module.mapper.xinli.UserMapper;
import com.example.buaadataplatform.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public List<UserEntity> queryUserList(int offset, int limit) {
        List<UserEntity> userEntityList = userMapper.queryUserAll();
//        if(offset>=userEntityList.size()){
//            return new ArrayList<>();
//        }else{
//            return userEntityList.subList(offset, (offset+limit)<userEntityList.size()?(offset+limit-1):(userEntityList.size()));
//        }
        return userEntityList;
    }

    @Override
    public UserEntity queryUserById(int user_id) {
        return userMapper.queryUserById(user_id);
    }

    @Override
    public boolean updateUser(UserEntity userEntity) {
        return userMapper.updateById(userEntity)==1?true:false;
    }

    @Override
    public boolean delteUser(int userId) {
        if(userMapper.deleteById(userId)==0)
            return false;
        else
            return true;
    }

    @Override
    public UserAgeVO ageCount() {
        UserAgeVO userAgeVO = new UserAgeVO();
        userAgeVO.setUnder_eighteen(userMapper.selectAgeCnt(0,17));
        userAgeVO.setEighteen_to_twentyfive(userMapper.selectAgeCnt(18,24));
        userAgeVO.setTwentyfive_to_thirtyfive(userMapper.selectAgeCnt(25,34));
        userAgeVO.setThirtyfive_to_fortyfive(userMapper.selectAgeCnt(35,44));
        userAgeVO.setOther(userMapper.selectAgeCnt(45,1000));
        return userAgeVO;
    }

    @Override
    public boolean login(UserEntity userEntity, HttpSession session) {
        UserEntity user = userMapper.userLogin(userEntity);
        if(user==null)
            return false;
        session.setAttribute("authority", user.getAuthority());
        session.setAttribute("user_id", user.getUserid());
        return true;
    }

    @Override
    public boolean logout(HttpSession session) {
        if(session.getAttribute("authority")!=null&&session.getAttribute("user_id")!=null){
            session.invalidate();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean insertUser(UserEntity userEntity) {
//        userEntity.setAuthority(Authority.NORMAL_USER);
        return userMapper.register(userEntity)==1?true:false;
    }

    @Override
    public List<UserEntity> mohuQueryUser(String username) {
        return userMapper.selectList(new QueryWrapper<UserEntity>().select("userid", "username").like("username",username));
    }

    @Override
    public boolean updatePassword(String password, HttpSession session) {
        int user_id = (Integer)session.getAttribute("user_id");
        UserEntity userEntity = userMapper.selectById(user_id);
        userEntity.setPassword(password);
        return userMapper.updateById(userEntity)==1?true:false;
    }
}
