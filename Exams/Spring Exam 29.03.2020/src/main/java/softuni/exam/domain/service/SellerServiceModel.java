package softuni.exam.domain.service;

import org.hibernate.validator.constraints.Length;
import softuni.exam.domain.entities.enums.Rating;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SellerServiceModel {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Rating rating;
    private String town;

    public SellerServiceModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Length(min = 2, max = 19)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Length(min = 2, max = 19)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Pattern(regexp = "^.+@.+\\..+$")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @NotNull
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
