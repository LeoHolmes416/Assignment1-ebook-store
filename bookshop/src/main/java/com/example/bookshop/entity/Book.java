package com.example.bookshop.entity;

import lombok.Data;

@Data
public class Book {
    //主键ID
    private Integer id;
    //书名
    private String bookName;
    //作者
    private String bookAuthor;
    //类型
    private Integer bookType;
    //简介
    private String introduction;
    //图片
    private String bookImage;
    //章节数
    private Integer pages;


}