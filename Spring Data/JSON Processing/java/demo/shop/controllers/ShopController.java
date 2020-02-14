package demo.shop.controllers;

import demo.shop.domain.models.view.categorymodels.CategoryStatsModel;
import demo.shop.domain.models.view.categorymodels.ListCategoryStatsModel;
import demo.shop.domain.models.view.productsmodels.ListSellerProductModel;
import demo.shop.domain.models.view.productsmodels.SellerProductModel;
import demo.shop.domain.models.view.usersmodels.ListUserWithSoldProductsModel;
import demo.shop.domain.models.view.usersmodels.UserWithSoldProductsModel;
import demo.shop.domain.models.view.usersmodels.statsmodels.AllUserStatsModel;
import demo.shop.domain.models.view.usersmodels.statsmodels.UserStatsModel;
import demo.shop.services.CategoryService;
import demo.shop.services.ProductService;
import demo.shop.services.UserService;
import demo.shop.util.exporters.FileDataWriter;
import demo.shop.util.parsers.ParserImpl;
import demo.shop.util.parsers.Parser;
import demo.shop.util.seeders.CategorySeeder;
import demo.shop.util.seeders.ProductSeeder;
import demo.shop.util.seeders.Seeder;
import demo.shop.util.seeders.UserSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        final String CATEGORIES_FILENAME = "categories";
        final String USERS_FILENAME = "users";
        final String PRODUCTS_FILENAME = "products";
//
        Parser parser = new ParserImpl();
//
        Seeder userSeeder = new UserSeeder(this.userService, parser);
        Seeder categoriesSeeder = new CategorySeeder(this.categoryService, parser);
        Seeder productsSeeder = new ProductSeeder(this.productService, this.userService, this.categoryService, parser);

        //choose to import data from either JSON or XML files
        // data is the same in both of them

//        fills the db with data from JSON resource files
//        userSeeder.SeedDbFromJson(USERS_FILENAME);
//        categoriesSeeder.SeedDbFromJson(CATEGORIES_FILENAME);
//        productsSeeder.SeedDbFromJson(PRODUCTS_FILENAME);

//        fills the db with data from xml resource files
//        userSeeder.SeedDbFromXML(USERS_FILENAME);
//        categoriesSeeder.SeedDbFromXML(CATEGORIES_FILENAME);
//        productsSeeder.SeedDbFromXML(PRODUCTS_FILENAME);



        //parses model data into both json and xml formats and writes it into files

        // #1
        FileDataWriter fileDataWriter = new FileDataWriter(parser);


//
        List<SellerProductModel> products = this.productService
                .getAllProductsWithoutBuyerInPriceRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        ListSellerProductModel listSellerProductModel = new ListSellerProductModel();
        listSellerProductModel.setProducts(products);

        String productsFileName = "products-without-buyers";
        fileDataWriter.exportDataToXmlFile(productsFileName, listSellerProductModel);
        fileDataWriter.exportDataToJsonFile(productsFileName, products);



//         #2
        List<UserWithSoldProductsModel> sellers = this.userService.getAllUsersWithSales();

        ListUserWithSoldProductsModel listUserWithSoldProductsModel = new ListUserWithSoldProductsModel();
        listUserWithSoldProductsModel.setSellers(sellers);

        String sellersFilename = "sellers";
        fileDataWriter.exportDataToJsonFile(sellersFilename, sellers);
        fileDataWriter.exportDataToXmlFile(sellersFilename, listUserWithSoldProductsModel);

//         #3
        List<CategoryStatsModel> categoryStats = this.categoryService.getAllCategoryStatistics();

        ListCategoryStatsModel listCategoryStatsModel = new ListCategoryStatsModel();
        listCategoryStatsModel.setCategories(categoryStats);

        String statsFilename = "category-stats";
        fileDataWriter.exportDataToJsonFile(statsFilename, categoryStats);
        fileDataWriter.exportDataToXmlFile(statsFilename, listCategoryStatsModel);

//         #4

        List<UserStatsModel> sellerStats = this.userService.getAllUserSellersStats();
        AllUserStatsModel allUserStatsModel = new AllUserStatsModel();

        allUserStatsModel.setUserCount(sellerStats.size());
        allUserStatsModel.setUsers(sellerStats);

        String sellersStatsFilename = "all-sellers-stats";
        fileDataWriter.exportDataToJsonFile(sellersStatsFilename, allUserStatsModel);
        fileDataWriter.exportDataToXmlFile(sellersStatsFilename, allUserStatsModel);
    }
}