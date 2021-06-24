package com.example.bookshop.mapper;

import com.example.bookshop.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from tb_user where username=#{username}")
    User findByUsername(@Param("username")String username);
    @Select("select * from tb_user where username=#{username} and password = #{password}")
    User findByUsernameAndPwd(@Param("username")String username,@Param("password")String password);



}