package demo.shop.domain.models.view.usersmodels.statsmodels;

import demo.shop.domain.models.view.productsmodels.SimpleProductModel;

import java.util.List;

public class SoldProducts {
    private int count;
    private List<SimpleProductModel> products;

    public SoldProducts() {
    }

    public List<SimpleProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<SimpleProductModel> products) {
        this.products = products;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}