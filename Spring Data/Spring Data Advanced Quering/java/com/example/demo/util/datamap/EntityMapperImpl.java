package com.example.demo.util.datamap;

import com.example.demo.domain.entities.Author;
import com.example.demo.domain.entities.Book;
import com.example.demo.domain.entities.Category;
import com.example.demo.domain.entities.enums.AgeRestriction;
import com.example.demo.domain.entities.enums.EditionType;
import com.example.demo.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class EntityMapperImpl implements EntityMapper {
    private AuthorRepository authorRepository;
    private Random random;
    private final int AUTHORS_COUNT = 30;

    public EntityMapperImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.random = new Random();
    }

    @Override
    public Book mapBook(String data) throws ParseException {
        String[] args = data.split("\\s+");

        EditionType editionType = EditionType.values()[Integer.parseInt(args[0])];
        SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
        Date releaseDate = formatter.parse(args[1]);

        Integer copies = Integer.parseInt(args[2]);
        BigDecimal price = new BigDecimal(args[3]);
        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(args[4])];

        Integer randomAuthorId = this.random.nextInt(AUTHORS_COUNT);
        Author author = this.authorRepository.findAuthorById(randomAuthorId);

        StringBuilder titleBuilder = new StringBuilder();
        for (int i = 5; i < args.length; i++) {
            titleBuilder.append(args[i]).append(" ");
        }

        titleBuilder.delete(titleBuilder.lastIndexOf(" "), titleBuilder.lastIndexOf(" "));
        String title = titleBuilder.toString().trim();

        Book book = new Book();
        book.setEditionType(editionType);
        book.setReleaseDate(releaseDate);
        book.setPrice(price);
        book.setAgeRestriction(ageRestriction);
        book.setTitle(title);
        book.setAuthor(author);
        book.setCopies(copies);

        return book;
    }

    @Override
    public Author mapAuthor(String data) {
        String[] names = data.split("\\s+");
        String firstName = names[0];
        String lastName = names[1];

        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        return author;
    }

    @Override
    public Category mapCategory(String data) {
        Category category = new Category();
        category.setName(data);

        return category;
    }
}
