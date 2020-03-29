package softuni.exam.domain.service;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class OfferServiceModel {

    private long id;
    private BigDecimal price;
    private String description;
    private boolean hasGoldStatus;
    private LocalDateTime addedOn;
    private CarServiceModel car;
    private Set<PictureServiceModel> pictures;
    private SellerServiceModel seller;

    public OfferServiceModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Length(min = 5)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public CarServiceModel getCar() {
        return car;
    }

    public void setCar(CarServiceModel car) {
        this.car = car;
    }

    public Set<PictureServiceModel> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureServiceModel> pictures) {
        this.pictures = pictures;
    }

    public SellerServiceModel getSeller() {
        return seller;
    }

    public void setSeller(SellerServiceModel seller) {
        this.seller = seller;
    }
}
