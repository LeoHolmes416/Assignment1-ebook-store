package com.example.bookshop.mapper;

import com.example.bookshop.entity.Book;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookMapper {
    List<Book> list();
    List<Book> listBookByUser(Integer uid );
    @Select("select * from tb_book where id = #{id}")
    Book findById(Integer id );

}