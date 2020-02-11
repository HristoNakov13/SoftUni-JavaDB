package demo.shop.controllers;

import demo.shop.domain.models.plainmodels.usersmodels.UserWithSoldProductsModel;
import demo.shop.services.CategoryService;
import demo.shop.services.ProductService;
import demo.shop.services.UserService;
import demo.shop.util.exporters.JsonFileWriter;
import demo.shop.util.parsers.GsonParser;
import demo.shop.util.parsers.Parser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

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
        Parser parser = new GsonParser();

//        final String CATEGORIES_FILENAME = "categories.json";
//        final String USERS_FILENAME = "users.json";
//        final String PRODUCTS_FILENAME = "products.json";
//
//        Seeder userSeeder = new UserSeeder(this.userService, parser);
//        userSeeder.jsonSeedDb(USERS_FILENAME);
//
//        Seeder categoriesSeeder = new CategorySeeder(this.categoryService, parser);
//        categoriesSeeder.jsonSeedDb(CATEGORIES_FILENAME);
//
//        Seeder productsSeeder = new ProductSeeder(this.productService, this.userService, this.categoryService, parser);
//        productsSeeder.jsonSeedDb(PRODUCTS_FILENAME);
//

        //parses model data into json and writes it into files
        // #1
        JsonFileWriter jsonFileWriter = new JsonFileWriter(parser);

//        List<SellingProductModel> products = this.productService
//                .getAllProductsWithoutBuyerInPriceRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
//
//        String productsFileName = "products-without-buyers";
//        jsonFileWriter.exportDataToFile(productsFileName, products);


        // #2
        List<UserWithSoldProductsModel> sellers = this.userService.getAllUsersWithSales();
        String sellersFilename = "sellers";
        jsonFileWriter.exportDataToFile(sellersFilename, sellers);

        System.out.println();


    }
}
