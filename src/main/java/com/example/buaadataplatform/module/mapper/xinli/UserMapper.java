package com.example.buaadataplatform.module.mapper.xinli;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.buaadataplatform.module.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    public List<UserEntity> queryUserAll();

    public UserEntity queryUserById(int user_id);

    public int updateUser(UserEntity userEntity);

    public int delteUser(int user_id);

    public int selectAgeCnt(int age1, int age2);

    public UserEntity userLogin(UserEntity userEntity);

    public int register(UserEntity userEntity);
}
