package softuni.exam.domain.view;

import java.math.BigDecimal;

public class PlayerSalaryViewModel {

    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private Integer number;
    private TeamPlayerSalaryViewModel team;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public TeamPlayerSalaryViewModel getTeam() {
        return team;
    }

    public void setTeam(TeamPlayerSalaryViewModel team) {
        this.team = team;
    }
}
