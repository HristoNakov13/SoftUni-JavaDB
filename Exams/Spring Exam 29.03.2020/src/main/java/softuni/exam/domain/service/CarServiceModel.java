package softuni.exam.domain.service;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class CarServiceModel {

    private long id;
    private String make;
    private String model;
    private Integer kilometers;
    private LocalDate registeredOn;

    public CarServiceModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Length(min = 2, max = 19)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Length(min = 2, max = 19)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Positive
    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }
}
