package demo.shop.domain.models.createmodels;

import demo.shop.domain.models.CategoryModel;
import demo.shop.domain.models.UserModel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductCreateModel {
    private String name;
    private BigDecimal price;
    private UserModel seller;
    private UserModel buyer;
    private Set<CategoryModel> categories = new HashSet<>();

    public ProductCreateModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryModel> categories) {
        this.categories = categories;
    }

    public UserModel getSeller() {
        return seller;
    }

    public void setSeller(UserModel seller) {
        this.seller = seller;
    }

    public UserModel getBuyer() {
        return buyer;
    }

    public void setBuyer(UserModel buyer) {
        this.buyer = buyer;
    }
}
