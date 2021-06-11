package com.example.bookshop.mapper;

import com.example.bookshop.entity.UB;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UBMapper {
    @Insert("insert into tb_user_book values(#{uid},#{bid})")
    public void insert(UB ub );

    @Select("select count(1) from tb_user_book where uid = #{uid} and bid = #{bid}")
    int findDuplicate(UB ub);

}