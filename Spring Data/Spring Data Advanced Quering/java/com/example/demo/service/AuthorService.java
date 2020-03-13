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

    private static final String PROCEDURE_NOT_FOUND_MSG = "PROCEDURE books_system.udp_get_books_count_by_author_first_last_names does not exist";
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

        content.forEach(line -> {
            Author author = this.entityMapper.mapAuthor(line);
            this.authorRepository.save(author);
        });
    }

    public String getNamesByFirstNameEndingWith(String endsWith) {
        List<Author> authors = this.authorRepository.findAllByFirstNameEndsWith(endsWith);

        return authors.stream()
                .map(author -> String.format("%s %s", author.getFirstName(), author.getLastName()))
                .collect(Collectors.joining("\r\n"));
    }

    @Transactional
    public String getBooksByAuthorLastNameStartingWith(String nameStartWith) {
        List<Author> authors = this.authorRepository.findAllByLastNameStartingWith(nameStartWith);
        StringBuilder bookInfo = new StringBuilder();

        return authors.stream()
                .map(author -> {
                    bookInfo.setLength(0);
                    author.getBooks()
                            .forEach(book -> {
                                bookInfo.append(book.getTitle());
                                bookInfo.append(String.format("(%s %s)", author.getFirstName(), author.getLastName()))
                                        .append(System.lineSeparator());
                            });

                    return bookInfo.toString();
                }).collect(Collectors.joining("\r\n"));
    }

    //uses Map as middleman for the sort for hopefully better performance
    //compared to splitting the authorInfo String to extract the copies number after the map()
    //or making another sum stream before the authors map()
    @Transactional
    public String getAllAuthorsAndBookCopiesCount() {
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
                .collect(Collectors.joining("\r\n"));
    }

    public String getTotalBooksCountByAuthorFullName(String fullName) {
        String[] nameSplit = fullName.split(" ");
        String firstName = nameSplit[0];
        String lastName = nameSplit[1];
        int booksCount;

        //try-catch block checks if the exception thrown is due to missing the needed procedure in the database
        //if that is the case a message about it is returned otherwise the exception is thrown again
        try {
            booksCount = this.authorRepository.getBooksCountByFirstAndLastName(firstName, lastName);
        } catch (Exception e) {
            String exceptionMessage = e.getCause().getCause().getMessage();

            if (exceptionMessage.equals(PROCEDURE_NOT_FOUND_MSG)) {
                return "Please make sure you added the procedure to the database.";
            }

            throw e;
        }

        return booksCount == 0
                ? String.format("%s has not written any books yet", fullName)
                : String.format("%s has written %s books", fullName, booksCount);
    }
}