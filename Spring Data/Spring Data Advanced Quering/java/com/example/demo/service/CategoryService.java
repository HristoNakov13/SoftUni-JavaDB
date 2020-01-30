package com.example.demo.service;

import com.example.demo.domain.entities.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.util.filereader.FileReader;
import com.example.demo.util.datamap.EntityMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final EntityMapper entityMapper;
    private final FileReader fileReader;

    public CategoryService(EntityMapper entityMapper, FileReader fileReader, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.fileReader = fileReader;
        this.entityMapper = entityMapper;
    }

    public void seedDb(String fileName) throws IOException {
        List<String> content = this.fileReader.getFileContent(fileName);

        content.forEach(line -> {
            Category category = this.entityMapper.mapCategory(line);
            this.categoryRepository.save(category);
        });
    }
}
