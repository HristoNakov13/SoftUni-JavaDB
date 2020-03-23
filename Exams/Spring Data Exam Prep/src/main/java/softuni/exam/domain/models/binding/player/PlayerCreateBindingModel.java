package softuni.exam.domain.models.binding.player;

import org.hibernate.validator.constraints.Length;
import softuni.exam.domain.entities.enums.Position;
import softuni.exam.domain.models.binding.picture.PictureCreateBindingModel;
import softuni.exam.domain.models.binding.team.TeamCreateBindingModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PlayerCreateBindingModel {

    private String firstName;
    private String lastName;
    private Integer number;
    private BigDecimal salary;
    private Position position;
    private PictureCreateBindingModel picture;
    private TeamCreateBindingModel team;

    public PlayerCreateBindingModel() {
    }

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Length(min = 3, max = 15)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    @Min(value = 1)
    @Max(value = 99)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @NotNull
    @Min(value = 0)
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @NotNull
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @NotNull
    public PictureCreateBindingModel getPicture() {
        return picture;
    }

    public void setPicture(PictureCreateBindingModel picture) {
        this.picture = picture;
    }

    @NotNull
    public TeamCreateBindingModel getTeam() {
        return team;
    }

    public void setTeam(TeamCreateBindingModel team) {
        this.team = team;
    }
}
