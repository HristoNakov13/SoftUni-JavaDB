package com.example.demo.service;

import com.example.demo.domain.entities.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.dbseeder.DbSeederImpl;
import com.example.demo.util.datamap.EntityMapper;
import com.example.demo.util.filereader.FileUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService extends DbSeederImpl {
    private BookRepository bookRepository;
    public BookService(EntityMapper entityMapper, FileUtil fileReader, BookRepository bookRepository) {
        super(entityMapper, fileReader);
        this.bookRepository = bookRepository;
    }

    @Override
    public void seedDb(String fileName) throws IOException {
        List<String> content = super.getFileContent(fileName);

        content.forEach(line -> {
            try {
                Book book = super.getEntityMapper().mapBook(line);
                this.bookRepository.save(book);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    public List<String> getBookTitlesAfterDate(Date date) {
        List<Book> result = this.bookRepository.findAllByReleaseDateAfter(date);

        return result.stream().map(Book::getTitle).collect(Collectors.toList());
    }
}
