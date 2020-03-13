package com.example.demo.service;

import com.example.demo.domain.entities.Book;
import com.example.demo.domain.entities.enums.AgeRestriction;
import com.example.demo.domain.entities.enums.EditionType;
import com.example.demo.domain.models.ReducedBook;
import com.example.demo.repository.BookRepository;
import com.example.demo.util.filereader.FileReader;
import com.example.demo.util.datamap.EntityMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final int INVALID_SQL_DATETIME_FORMAT = 9999;

    private final BookRepository bookRepository;
    private final EntityMapper entityMapper;
    private final FileReader fileReader;
    private ModelMapper modelMapper;

    public BookService(EntityMapper entityMapper, FileReader fileReader, BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.fileReader = fileReader;
        this.entityMapper = entityMapper;
        this.modelMapper = modelMapper;
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

    public String getTitlesAfterDate(Date date) {
        List<Book> result = this.bookRepository.findAllByReleaseDateAfter(date);

        return result.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining("\r\n"));
    }

    public String getTitlesByAgeRestriction(String input) {
        AgeRestriction ageRestriction;
        try {
            ageRestriction = AgeRestriction.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Invalid age restriction.";
        }

        List<Book> books = this.bookRepository.findAllByAgeRestriction(ageRestriction);

        return books.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining("\r\n"));
    }

    public String getBookTitlesByEditionAndCopiesLessThan(String edition, int copiesCount) {
        EditionType goldenEdition;
        try {
            goldenEdition = EditionType.valueOf(edition.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Invalid edition";
        }

        List<Book> books = this.bookRepository
                .findAllByEditionTypeAndCopiesLessThan(goldenEdition, copiesCount);

        return books.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining("\r\n"));
    }

    public String getTitlesByPriceLowerThanOrPriceHigherThan(BigDecimal lowerThan, BigDecimal higherThan) {
        List<Book> books = this.bookRepository
                .findAllByPriceLessThanOrPriceGreaterThan(lowerThan, higherThan);

        return books.stream()
                .map(book -> String.format("%s - $%s", book.getTitle(), book.getPrice()))
                .collect(Collectors.joining("\r\n"));
    }

    public String getTitlesByReleaseDateDifferentThanYear(int year) throws ParseException {
        if (year < -INVALID_SQL_DATETIME_FORMAT || year > INVALID_SQL_DATETIME_FORMAT) {
            return "Invalid year format.";
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy");
        Date releaseDateAfter = formatter.parse("31/12/" + year);
        Date releaseBefore = formatter.parse("01/01/" + year);

        List<Book> books = this.bookRepository.findAllByReleaseDateAfterOrReleaseDateBefore(releaseBefore, releaseDateAfter);


        return books.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining("\r\n"));
    }

    public String getTitlesByReleaseDateBefore(Date date) {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(date);

        return books.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining("\r\n"));
    }

    public String getTitlesByTitleContaining(String input) {
        List<Book> books = this.bookRepository.findAllByTitleContaining(input);

        return books.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining("\r\n"));
    }

    public int getBookCountWithTitleLengthLongerThan(int characters) {
        return this.bookRepository.findCountByTitleLongerThan(characters);
    }

    public ReducedBook getReducedBookByTitle(String title) {
        Book book = this.bookRepository.findBookByTitle(title);

        if (book == null) {
            throw new IllegalArgumentException(String.format("Book with title: %s does not exist.", title));
        }

        return this.modelMapper.map(book, ReducedBook.class);
    }

    public int increaseCopiesForBooksReleasedAfter(Date releasedAfter, int increaseBy) {
        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(releasedAfter);

        books.forEach(book -> {
            book.setCopies(book.getCopies() + increaseBy);
            this.bookRepository.save(book);
        });

        int totalBookCopiesAdded = books.size() * increaseBy;

        return totalBookCopiesAdded;
    }

    public String deleteBooksByCopiesLowerThan(int copiesCount) {
        List<Book> books = this.bookRepository.findAllByCopiesLessThan(copiesCount);
        this.bookRepository.deleteAll(books);

        return String.format("%s books were deleted",
                books.size());
    }
}