package com.example.demo.domain.models;

import com.example.demo.domain.entities.enums.AgeRestriction;
import com.example.demo.domain.entities.enums.EditionType;

import java.math.BigDecimal;

public class ReducedBook {
    private String title;
    private EditionType editionType;
    private AgeRestriction ageRestriction;
    private BigDecimal price;

    public ReducedBook() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
