package demo.shop.domain.models.view.usersmodels.statsmodels;

import demo.shop.domain.models.view.productsmodels.SimpleProductModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProducts {
    @XmlAttribute(name = "count")
    private int count;

    @XmlElement(name = "product")
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