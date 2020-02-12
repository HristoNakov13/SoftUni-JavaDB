package demo.shop.domain.models.view.productsmodels;

import java.math.BigDecimal;

public class SimpleProductModel {
    private String name;
    private BigDecimal price;

    public SimpleProductModel() {
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
}