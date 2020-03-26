package hiberspring.domain.dtos;

import javax.validation.constraints.NotNull;

public class TownSeedDto {

    private String name;
    private Integer population;

    public TownSeedDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
