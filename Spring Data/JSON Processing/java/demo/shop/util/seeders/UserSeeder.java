package demo.shop.util.seeders;

import demo.shop.domain.models.createmodels.UserCreateModel;
import demo.shop.services.UserService;
import demo.shop.util.parsers.Parser;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UserSeeder extends SeederImpl {
    private UserService userService;
    private Parser parser;

    public UserSeeder(UserService userService, Parser parser) {
        this.userService = userService;
        this.parser = parser;
    }

    @Override
    public void jsonSeedDb(String resourceFilename) throws IOException {
        File usersFile = new ClassPathResource(resourceFilename).getFile();
        String usersJson = super.getFileContent(usersFile);

        List<UserCreateModel> users = Arrays.asList(this.parser.fromJSon(usersJson, UserCreateModel[].class));
        this.userService.saveAllToDb(users);
    }
}
