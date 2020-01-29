package com.example.demo.util.datamap;

import com.example.demo.domain.entities.Author;
import com.example.demo.domain.entities.Book;
import com.example.demo.domain.entities.Category;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

@Component
public interface EntityMapper {
    Book mapBook(String data) throws ParseException;

    Author mapAuthor(String data);

    Category mapCategory(String data);
}
