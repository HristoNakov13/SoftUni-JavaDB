package com.example.demo.controller;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);

////        #1
//        this.authorService.seedDb(AUTHORS_FILENAME);
//        this.categoryService.seedDb(CATEGORIES_FILENAME);
//        this.bookService.seedDb(BOOKS_FILENAME);


//        #2
//        Date searchDate = Date.valueOf("2000-01-01");
//        List<String> bookTitles = this.bookService.getBookTitlesAfterDate(searchDate);
//        bookTitles.forEach(System.out::println);

////        #3
//        Date searchDate = Date.valueOf("1990-01-01");
//        int bookCount = 1;


//------------------------------------------------------------------------


//        #2.1
//        String ageRestriction = scanner.nextLine();
//
//        List<String> titles = this.bookService.getBookTitlesByAgeRestriction(ageRestriction);
//        titles.forEach(System.out::println);

//        #2.2
//        List<String> goldenBooks = this.bookService.getGoldenEditionBookTitles();
//        goldenBooks.forEach(System.out::println);

//        #2.3
//        List<String> booksByPrice = this.bookService.getTitlesByPriceLowerThanFiveHigherThanForty();
//
//        booksByPrice.forEach(System.out::println);

//        #2.4
//        int year = Integer.parseInt(scanner.nextLine());
//        List<String> booksAfter = this.bookService.getTitlesByReleaseDateDifferentThanYear(year);
//
//        booksAfter.forEach(System.out::println);

//        #2.5
//        String input = scanner.nextLine();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
//        Date releaseDateBefore = formatter.parse(input);
//
//        List<String> booksBefore = this.bookService.getTitlesByReleaseDateBefore(releaseDateBefore);
//        booksBefore.forEach(System.out::println);

//        #2.6
//        String endsWith = scanner.nextLine();
//        List<String> authorsStartingWith = this.authorService.getNamesByFirstNameEndingWith(endsWith);
//
//        authorsStartingWith.forEach(System.out::println);

//        2.7
//        String keyword = scanner.nextLine();
//        List<String> titles = this.bookService.getTitlesByTitleContaining(keyword);
//
//        titles.forEach(System.out::println);

//        #2.8
//        String nameStartingWith = scanner.nextLine();
//
//        List<String> booksInfo = this.authorService.getBooksByAuthorLastNameStartingWith(nameStartingWith);
//
//        booksInfo.forEach(System.out::println);

//        #2.9
//        int charactersCount = Integer.parseInt(scanner.nextLine());
//        System.out.println(this.bookService.getBookCountWithTitleLongerThan(charactersCount));

//        #2.10
        List<String> authorsInfo = this.authorService.getAllAuthorsAndBookCopiesCount();

        authorsInfo.forEach(System.out::println);
    }
}
