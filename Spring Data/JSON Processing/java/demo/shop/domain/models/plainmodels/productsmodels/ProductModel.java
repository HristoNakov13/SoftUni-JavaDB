package demo.shop.domain.models.plainmodels.productsmodels;

import demo.shop.domain.entities.Category;

import java.math.BigDecimal;
import java.util.Set;

public class ProductModel {
    private String name;
    private BigDecimal price;
    private Set<Category> categories;

    public ProductModel() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
