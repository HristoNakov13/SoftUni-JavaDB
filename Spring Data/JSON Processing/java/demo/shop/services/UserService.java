package demo.shop.services;

import demo.shop.domain.entities.User;
import demo.shop.domain.models.plainmodels.usersmodels.UserWithSoldProductsModel;
import demo.shop.domain.models.plainmodels.usersmodels.UserModel;
import demo.shop.domain.models.createmodels.UserCreateModel;
import demo.shop.repositories.UserRepository;
import demo.shop.services.validators.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;
    private UserValidator userValidator;

    public UserService(UserRepository userRepository, ModelMapper mapper, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userValidator = userValidator;
    }

    public void saveToDb(UserCreateModel userCreateModel) {
        if (!this.userValidator.isValidUser(userCreateModel)) {
            return;
        }

        User user = mapper.map(userCreateModel, User.class);

        this.userRepository.save(user);
    }

    public void saveAllToDb(List<UserCreateModel> usersCreateModel) {
        usersCreateModel.stream()
                .filter(this.userValidator::isValidUser)
                .map(user -> this.mapper.map(user, User.class))
                .forEach(user -> this.userRepository.save(user));
    }

    public List<UserModel> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(user -> this.mapper.map(user, UserModel.class))
                .collect(Collectors.toList());
    }

    public List<UserWithSoldProductsModel> getAllUsersWithSales() {
        //filters products from the seller that were never sold
        List<User> sellers = this.userRepository.getAllUsersWithSales()
                .stream()
                .map(seller -> {
                     seller.setSellingProducts(seller.getSellingProducts()
                            .stream().filter(product -> product.getBuyer() != null)
                            .collect(Collectors.toSet()));
                    return seller;
                }).collect(Collectors.toList());

        return sellers.stream()
                .map(seller -> this.mapper.map(seller, UserWithSoldProductsModel.class))
                .collect(Collectors.toList());
    }
}