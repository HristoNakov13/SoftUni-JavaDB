package demo.shop.domain.models.plainmodels.usersmodels;

import demo.shop.domain.models.plainmodels.productsmodels.BoughtProductModel;

import java.util.Set;

public class UserWithSoldProductsModel {
    private String firstName;
    private String lastName;
    private Set<BoughtProductModel> soldProducts;

    public UserWithSoldProductsModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<BoughtProductModel> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<BoughtProductModel> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
