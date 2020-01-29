package com.example.demo.service;

import com.example.demo.domain.entities.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.dbseeder.DbSeederImpl;
import com.example.demo.util.datamap.EntityMapper;
import com.example.demo.util.filereader.FileUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryService extends DbSeederImpl {
    private CategoryRepository categoryRepository;

    public CategoryService(EntityMapper entityMapper, FileUtil fileReader, CategoryRepository categoryRepository) {
        super(entityMapper, fileReader);
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedDb(String fileName) throws IOException {
        List<String> content = super.getFileContent(fileName);

        content.forEach(line -> {
            Category category = super.getEntityMapper().mapCategory(line);
            this.categoryRepository.save(category);
        });
    }
}
