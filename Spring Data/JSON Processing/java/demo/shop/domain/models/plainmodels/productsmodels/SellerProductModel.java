package demo.shop.domain.models.plainmodels.productsmodels;

import java.math.BigDecimal;

public class SellerProductModel {
    private String name;
    private BigDecimal price;
    private String seller;

    public SellerProductModel() {
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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
