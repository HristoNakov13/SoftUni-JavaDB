package demo.shop.services.validators;

import demo.shop.domain.models.createmodels.ProductCreateModel;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {
    public ProductValidator() {
    }

    public boolean isValidProduct(ProductCreateModel product) {
        return this.isValidName(product.getName());
    }

    private boolean isValidName(String name) {
        return name != null && name.length() >= 3;
    }

}
