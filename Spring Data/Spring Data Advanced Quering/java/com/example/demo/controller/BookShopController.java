package com.example.demo.controller;

import com.example.demo.domain.models.ReducedBook;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.CategoryService;
import com.example.demo.util.enums.MonthNumericValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

//        #1
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
//        System.out.println("Age restriction:");
//        String ageRestriction = scanner.nextLine();
//
//        System.out.println(this.bookService.getTitlesByAgeRestriction(ageRestriction));


//        #2.2
//        String editionType = "gold";
//        int copiesCount = 5000;
//        System.out.println(this.bookService.getBookTitlesByEditionAndCopiesLessThan(editionType, copiesCount));

//        #2.3
//        BigDecimal lessThan = BigDecimal.valueOf(5);
//        BigDecimal higherThan = BigDecimal.valueOf(40);
//        System.out.println(this.bookService.getTitlesByPriceLowerThanOrPriceHigherThan(lessThan, higherThan));

//        #2.4
//        System.out.println("Not released in year:");
//        int year = Integer.parseInt(scanner.nextLine());
//        System.out.println(this.bookService.getTitlesByReleaseDateDifferentThanYear(year));


//        #2.5
//        System.out.println("Release date before:");
//        String input = scanner.nextLine();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
//        Date releaseDateBefore = formatter.parse(input);
//
//        System.out.println(this.bookService.getTitlesByReleaseDateBefore(releaseDateBefore));

//        #2.6
//        System.out.println("Author name ends with:");
//        String endsWith = scanner.nextLine();
//        System.out.println(this.authorService.getNamesByFirstNameEndingWith(endsWith));

//        #2.7
//        System.out.println("Title containing:");
//        String keyword = scanner.nextLine();
//        System.out.println(this.bookService.getTitlesByTitleContaining(keyword));

//        #2.8
//        System.out.println("Author last name starting with:");
//        String nameStartingWith = scanner.nextLine();
//
//        System.out.println(this.authorService.getBooksByAuthorLastNameStartingWith(nameStartingWith));

//        #2.9
//        System.out.println("Title longer than:");
//        int charactersCount = Integer.parseInt(scanner.nextLine());
//
//        String comment = String.format("There are %s books with longer title than %s symbols",
//                this.bookService.getBookCountWithTitleLengthLongerThan(charactersCount),
//                charactersCount);
//
//        System.out.println(comment);

//        #2.10
//        System.out.println(this.authorService.getAllAuthorsAndBookCopiesCount());

//        #2.11

//        System.out.println("Book title:");
//        String title = scanner.nextLine();
//
//        try {
//            ReducedBook reducedBook = this.bookService.getReducedBookByTitle(title);
//            String print = String.format("%s %s %s %s",
//                    reducedBook.getTitle(),
//                    reducedBook.getEditionType(),
//                    reducedBook.getAgeRestriction(),
//                    reducedBook.getPrice());
//            System.out.println(print);
//        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }

//        #2.12

//        System.out.println("Release date after:");
//        String[] releasedAfter = scanner.nextLine().split("\\s+");
//
//        System.out.println("Increase copies by:");
//        int increaseBy = Integer.parseInt(scanner.nextLine());
//
//        String day = releasedAfter[0];
//        int month = MonthNumericValues.valueOf(releasedAfter[1]).getMonthValue();
//        String year = releasedAfter[2];
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd M yyyy");
//        Date releaseDateAfter = formatter.parse(String.format("%s %s %s", day, month, year));
//
//        System.out.println(bookService.increaseCopiesForBooksReleasedAfter(releaseDateAfter, increaseBy));

//       #2.13
//        keep in mind the output might be different due to previous manipulations

//        System.out.println("Copies less than:");
//        int copiesCount = Integer.parseInt(scanner.nextLine());
//
//        System.out.println(this.bookService.deleteBooksByCopiesLowerThan(copiesCount));

//        #2.1
//        Results may vary due to the randomized author - book assignment
//        System.out.println("Author full name:");
//        String authorFullName = scanner.nextLine();
//        System.out.println(this.authorService.getTotalBooksCountByAuthorFullName(authorFullName));
    }
}
