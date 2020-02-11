package demo.shop.util.seeders;

import demo.shop.domain.models.plainmodels.categorymodels.CategoryModel;
import demo.shop.domain.models.plainmodels.usersmodels.UserModel;
import demo.shop.domain.models.createmodels.ProductCreateModel;
import demo.shop.services.CategoryService;
import demo.shop.services.ProductService;
import demo.shop.services.UserService;
import demo.shop.util.parsers.Parser;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ProductSeeder extends SeederImpl {
    private ProductService productService;
    private UserService userService;
    private CategoryService categoryService;
    private Parser parser;

    public ProductSeeder(ProductService productService, UserService userService, CategoryService categoryService, Parser parser) {
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.parser = parser;
    }

    //    When importing the products, randomly select the buyer and seller from the existing users.
    //    Leave out some products that have not been sold (i.e. buyer is null).
    //    Randomly generate categories for each product from the existing categories.

    @Override
    public void jsonSeedDb(String resourceFilename) throws IOException {
        File productsFile = new ClassPathResource(resourceFilename).getFile();
        String productsJson = super.getFileContent(productsFile);

        //lazy fetch breaks the nested models
        //really need to look into how to fix this
        List<UserModel> registeredUsers = this.userService.getAllUsers();
        List<CategoryModel> availableCategories = this.categoryService.getAllCategories();

        int categoriesLength = availableCategories.size();
        int registeredUsersLength = registeredUsers.size();
        double chanceForNoBuyer = availableCategories.size() / 5.0;

        Random rng = new Random();
        List<ProductCreateModel> products = Arrays.asList(this.parser.fromJSon(productsJson, ProductCreateModel[].class));

        for(ProductCreateModel product : products) {
            //numberOfCategories - count of how many categories a product will have
            //sellerIndex, buyerIndex, categoryIndex - random indexes from registeredUsers and availableCategories collections

            int buyerIndex = rng.nextInt(registeredUsersLength);
            int sellerIndex = rng.nextInt(registeredUsersLength);
            int numberOfCategories = rng.nextInt(categoriesLength);

            //roughly 20% chance a product will have no buyer
            //when chanceForNoBuyer > numberOfCategories comparison is made
            //lowered chance a bit further by comparing seller and buyer indices to avoid
            //to avoid seller being his own buyer

            if (chanceForNoBuyer < numberOfCategories || sellerIndex == buyerIndex ) {
                product.setBuyer(registeredUsers.get(buyerIndex));
            }
            product.setSeller(registeredUsers.get(sellerIndex));

            for (int i = 0; i < numberOfCategories / 2; i++) {
                int categoryIndex = rng.nextInt(categoriesLength);
                product.getCategories().add(availableCategories.get(categoryIndex));
            }
        }

        this.productService.saveAllToDb(products);
    }
}
