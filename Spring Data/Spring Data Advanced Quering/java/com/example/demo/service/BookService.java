package com.example.demo.service;

import com.example.demo.domain.entities.Book;
import com.example.demo.domain.entities.enums.AgeRestriction;
import com.example.demo.domain.entities.enums.EditionType;
import com.example.demo.repository.BookRepository;
import com.example.demo.util.filereader.FileReader;
import com.example.demo.util.datamap.EntityMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final EntityMapper entityMapper;
    private final FileReader fileReader;

    public BookService(EntityMapper entityMapper, FileReader fileReader, BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.fileReader = fileReader;
        this.entityMapper = entityMapper;
    }

    public void seedDb(String fileName) throws IOException {
        List<String> content = this.fileReader.getFileContent(fileName);

        content.forEach(line -> {
            try {
                Book book = this.entityMapper.mapBook(line);
                this.bookRepository.save(book);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    public List<String> getTitlesAfterDate(Date date) {
        List<Book> result = this.bookRepository.findAllByReleaseDateAfter(date);

        return result.stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    public List<String> getTitlesByAgeRestriction(String input) {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(input.toUpperCase());
        List<Book> books = this.bookRepository.findAllByAgeRestriction(ageRestriction);

        return books.stream()
                .map(book -> String.format("%s %s", book.getAgeRestriction().name(), book.getTitle()))
                .collect(Collectors.toList());
    }

    public List<String> getGoldenEditionTitles() {
        EditionType goldenEdition = EditionType.GOLD;
        int GOLDEN_BOOKS_COPIES_REQUIREMENT = 5000;

        List<Book> books = this.bookRepository
                .findAllByEditionTypeAndCopiesLessThan(goldenEdition, GOLDEN_BOOKS_COPIES_REQUIREMENT);

        return books.stream()
                .map(book -> String.format("%s %s %s", book.getEditionType().name(), book.getCopies(), book.getTitle()))
                .collect(Collectors.toList());
    }

    public List<String> getTitlesByPriceLowerThanFiveHigherThanForty() {
        BigDecimal PRICE_LOWER_THAN = BigDecimal.valueOf(5);
        BigDecimal PRICE_HIGHER_THAN = BigDecimal.valueOf(40);

        List<Book> books = this.bookRepository
                .findAllByPriceGreaterThanOrPriceLessThan(PRICE_HIGHER_THAN, PRICE_LOWER_THAN);

        return books.stream()
                .map(book -> String.format("%s %s", book.getTitle(), book.getPrice()))
                .collect(Collectors.toList());
    }

    public List<String> getTitlesByReleaseDateDifferentThanYear(int year) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy");

        Date releaseDateAfter = formatter.parse("31/12/" + year);
        Date releaseBefore = formatter.parse("01/01/" + year);

        List<Book> books = this.bookRepository.findAllByReleaseDateAfterOrReleaseDateBefore(releaseBefore, releaseDateAfter);

        return books.stream().map(Book::getTitle).collect(Collectors.toList());
    }

    public List<String> getTitlesByReleaseDateBefore(Date date) {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(date);

        return books.stream().map(Book::getTitle).collect(Collectors.toList());
    }

    public List<String> getTitlesByTitleContaining(String input) {
        List<Book> books = this.bookRepository.findAllByTitleContaining(input);

        return books.stream().map(Book::getTitle).collect(Collectors.toList());
    }

    public int getBookCountWithTitleLongerThan(int characters) {
        return this.bookRepository.findCountByTitleLongerThan(characters);
    }
}