package softuni.exam.domain.service;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class PictureServiceModel {

    private long id;
    private String name;
    private LocalDateTime dateAndTime;
    private CarServiceModel car;

    public PictureServiceModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Length(min = 2, max = 19)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public CarServiceModel getCar() {
        return car;
    }

    public void setCar(CarServiceModel car) {
        this.car = car;
    }
}
