package hiberspring.domain.dtos;

import javax.validation.constraints.NotNull;

public class EmployeeCardSeedDto {

    private String number;

    public EmployeeCardSeedDto() {
    }

    @NotNull
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
