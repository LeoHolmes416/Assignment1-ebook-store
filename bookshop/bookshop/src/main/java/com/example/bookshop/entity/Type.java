package com.example.bookshop.entity;

import lombok.Data;

@Data
public class Type {
    //主键ID
    private Integer id;
    //父ID
    private Integer parentId;
    //类型名
    private String typeName;

}