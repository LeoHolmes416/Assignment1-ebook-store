package com.example.bookshop.dto;

import com.example.bookshop.entity.Book;
import lombok.Data;

@Data
public class BookDTO extends Book {
    private String typeName;
}
