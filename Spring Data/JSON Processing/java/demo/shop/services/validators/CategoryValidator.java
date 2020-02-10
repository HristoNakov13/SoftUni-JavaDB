package demo.shop.services.validators;

import demo.shop.domain.models.createmodels.CategoryCreateModel;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {
    public CategoryValidator() {
    }

    public boolean isValidCategory(CategoryCreateModel category) {
        return this.isValidName(category.getName());
    }

    private boolean isValidName(String name) {
        return name != null && name.length() >= 3 && name.length() <= 15;
    }
}
