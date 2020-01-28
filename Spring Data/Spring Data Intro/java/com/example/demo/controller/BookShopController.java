package com.example.demo.controller;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.sql.Date;
import java.util.List;

@Controller
public class BookShopController implements CommandLineRunner {
    private AuthorService authorService;
    private BookService bookService;
    private CategoryService categoryService;

    @Autowired
    public BookShopController(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {
        String AUTHORS_FILENAME = "authors.txt";
        String BOOKS_FILENAME = "books.txt";
        String CATEGORIES_FILENAME = "categories.txt";

//        #1
//        this.authorService.seedDb(AUTHORS_FILENAME);
//        this.categoryService.seedDb(CATEGORIES_FILENAME);
//        this.bookService.seedDb(BOOKS_FILENAME);


//        #2
//        Date searchDate = Date.valueOf("2000-01-01");
//        List<String> bookTitles = this.bookService.getBookTitlesAfterDate(searchDate);
//        bookTitles.forEach(System.out::println);

//        #3
        Date searchDate = Date.valueOf("1990-01-01");
        int bookCount = 1;

    }
}
