package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {


    @Select("select user.* from user where openid=#{openId}")
    User getUser(String openId);


    void addUser(User user);
}
