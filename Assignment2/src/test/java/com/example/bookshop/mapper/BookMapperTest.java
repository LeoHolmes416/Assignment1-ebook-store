package com.example.bookshop.mapper;


import com.example.bookshop.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan("com.example.bookshop.mapper")
public class BookMapperTest {

    @Resource
    BookMapper bookMapper;

    @Test
    public  void testList(){
        List<Book> list = bookMapper.list();
        System.out.println(list);
    }



}