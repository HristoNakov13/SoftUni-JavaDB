package com.example.demo.service;

import com.example.demo.domain.entities.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.service.dbseeder.DbSeederImpl;
import com.example.demo.util.datamap.EntityMapper;
import com.example.demo.util.filereader.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AuthorService extends DbSeederImpl {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(EntityMapper entityMapper, AuthorRepository authorRepository, FileUtil fileReader) {
        super(entityMapper, fileReader);
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedDb(String fileName) throws IOException {
       List<String> content = super.getFileContent(fileName);

       content.stream().forEach(line -> {
           Author author = super.getEntityMapper().mapAuthor(line);
           this.authorRepository.save(author);
       });
    }

    public Author findAuthorById(Integer id) {
        return this.authorRepository.findAuthorById(id);
    }
}
