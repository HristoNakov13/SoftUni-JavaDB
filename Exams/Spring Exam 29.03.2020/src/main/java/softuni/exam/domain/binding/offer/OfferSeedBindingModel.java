package softuni.exam.domain.binding.offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedBindingModel {

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "added-on")
    private String addedOn;

    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;

    @XmlElement(name = "price")
    private BigDecimal price;

    @XmlElement(name = "car")
    private CarElementSeedModel car;

    @XmlElement(name = "seller")
    private SellerElementSeedModel seller;

    public OfferSeedBindingModel() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CarElementSeedModel getCar() {
        return car;
    }

    public void setCar(CarElementSeedModel car) {
        this.car = car;
    }

    public SellerElementSeedModel getSeller() {
        return seller;
    }

    public void setSeller(SellerElementSeedModel seller) {
        this.seller = seller;
    }
}
