package com.example.demo.service;

import com.example.demo.domain.entities.Author;
import com.example.demo.domain.entities.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.util.filereader.FileReader;
import com.example.demo.util.datamap.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final EntityMapper entityMapper;
    private final FileReader fileReader;

    @Autowired
    public AuthorService(EntityMapper entityMapper, AuthorRepository authorRepository, FileReader fileReader) {
        this.authorRepository = authorRepository;
        this.entityMapper = entityMapper;
        this.fileReader = fileReader;
    }

    public void seedDb(String fileName) throws IOException {
        List<String> content = this.fileReader.getFileContent(fileName);

        content.stream().forEach(line -> {
            Author author = this.entityMapper.mapAuthor(line);
            this.authorRepository.save(author);
        });
    }

    public List<String> getNamesByFirstNameEndingWith(String endsWith) {
        List<Author> authors = this.authorRepository.findAllByFirstNameEndsWith(endsWith);

        return authors.stream()
                .map(author -> String.format("%s %s", author.getFirstName(), author.getLastName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<String> getBooksByAuthorLastNameStartingWith(String nameStartWith) {
        List<Author> authors = this.authorRepository.findAllByLastNameStartingWith(nameStartWith);

        StringBuilder bookInfo = new StringBuilder();

        return authors.stream().map(author -> {
            bookInfo.setLength(0);

            author.getBooks().forEach(book -> {
                bookInfo.append(book.getTitle());
                bookInfo.append(String.format("(%s %s)", author.getFirstName(), author.getLastName()))
                        .append(System.lineSeparator());
            });

            return bookInfo.toString();
        }).collect(Collectors.toList());
    }

    //uses Map as middleman for the sort for hopefully better performance
    //compared to splitting the authorInfo String to extract the copies number after the map()
    //or making another sum stream before the authors map()
    @Transactional
    public List<String> getAllAuthorsAndBookCopiesCount() {
        List<Author> authors = this.authorRepository.findAll();
        Map<String, Integer> authorsBookCount = new HashMap<>();

        return authors.stream()
                .map(author -> {
                    int copiesCount = author.getBooks().stream().mapToInt(Book::getCopies).sum();
                    String authorInfo = String.format("%s %s %d", author.getFirstName(), author.getLastName(), copiesCount);
                    authorsBookCount.put(authorInfo, copiesCount);

                    return authorInfo;
                })
                .sorted((a1, a2) -> authorsBookCount.get(a2) - authorsBookCount.get(a1))
                .collect(Collectors.toList());
    }
}