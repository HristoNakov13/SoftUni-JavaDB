package demo.shop.util.seeders;

import demo.shop.domain.models.createmodels.ListUserCreateModel;
import demo.shop.domain.models.createmodels.UserCreateModel;
import demo.shop.services.UserService;
import demo.shop.util.parsers.Parser;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBException;
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
    public void SeedDbFromJson(String resourceFilename) throws IOException {
        String usersJson = super.getFileContent(resourceFilename + ".json");

        List<UserCreateModel> users = Arrays.asList(this.parser.fromJSon(usersJson, UserCreateModel[].class));
        this.userService.saveAllToDb(users);
    }

    @Override
    public void SeedDbFromXML(String resourceFilename) throws IOException, JAXBException {
        String xml = super.getFileContent(resourceFilename + ".xml");

        ListUserCreateModel listUserCreateModel = this.parser.fromXML(xml, ListUserCreateModel.class);

        this.userService.saveAllToDb(listUserCreateModel.getUsers());
    }
}