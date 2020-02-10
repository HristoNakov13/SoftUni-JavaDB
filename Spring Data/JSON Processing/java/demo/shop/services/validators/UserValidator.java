package demo.shop.services.validators;

import demo.shop.domain.models.createmodels.UserCreateModel;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public UserValidator() {
    }

    public boolean isValidUser(UserCreateModel userCreateModel) {
        return this.isValidLastName(userCreateModel.getLastName());
    }

    private boolean isValidLastName(String lastName) {
        return lastName != null && lastName.length() >= 3;
    }
}
