package demo.shop.controllers;

import demo.shop.services.CategoryService;
import demo.shop.services.ProductService;
import demo.shop.services.UserService;
import demo.shop.util.parsers.GsonParser;
import demo.shop.util.parsers.Parser;
import demo.shop.util.seeders.CategorySeeder;
import demo.shop.util.seeders.ProductSeeder;
import demo.shop.util.seeders.Seeder;
import demo.shop.util.seeders.UserSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ShopController implements CommandLineRunner {
    private UserService userService;
    private CategoryService categoryService;
    private ProductService productService;

    public ShopController(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        //fills the db with data from JSON resource files

//        final String CATEGORIES_FILENAME = "categories.json";
//        final String USERS_FILENAME = "users.json";
//        final String PRODUCTS_FILENAME = "products.json";
//
//        Parser parser = new GsonParser();
//
//        Seeder userSeeder = new UserSeeder(this.userService, parser);
//        userSeeder.jsonSeedDb(USERS_FILENAME);
//
//        Seeder categoriesSeeder = new CategorySeeder(this.categoryService, parser);
//        categoriesSeeder.jsonSeedDb(CATEGORIES_FILENAME);
//
//        Seeder productsSeeder = new ProductSeeder(this.productService, this.userService, this.categoryService, parser);
//        productsSeeder.jsonSeedDb(PRODUCTS_FILENAME);
    }
}
