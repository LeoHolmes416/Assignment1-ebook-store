package com.example.bookshop.controller;

import com.example.bookshop.bean.RespBean;
import com.example.bookshop.entity.Type;
import com.example.bookshop.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("type")
public class BookTypeController {

    @Autowired
    TypeMapper typeMapper ;

    @GetMapping("/list")
    public RespBean index(){
        List<Type> list = typeMapper.list();
        return RespBean.success("成功",list);
    }



}
