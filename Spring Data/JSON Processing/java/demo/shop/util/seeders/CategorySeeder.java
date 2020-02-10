package demo.shop.util.seeders;

import demo.shop.domain.models.createmodels.CategoryCreateModel;
import demo.shop.services.CategoryService;
import demo.shop.util.parsers.Parser;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CategorySeeder extends SeederImpl {
    private CategoryService categoryService;
    private Parser parser;

    public CategorySeeder(CategoryService categoryService, Parser parser) {
        this.categoryService = categoryService;
        this.parser = parser;
    }

    @Override
    public void jsonSeedDb(String resourceFilename) throws IOException {
        File categoriesFile = new ClassPathResource(resourceFilename).getFile();
        String categoriesJson = super.getFileContent(categoriesFile);
        List<CategoryCreateModel> categories = Arrays.asList(this.parser.fromJSon(categoriesJson, CategoryCreateModel[].class));

        this.categoryService.saveAllToDb(categories);
    }
}
