package com.example.bookshop.mapper;

import com.example.bookshop.entity.Type;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TypeMapper {

    @Select("select * from tb_type")
    List<Type> list();
}