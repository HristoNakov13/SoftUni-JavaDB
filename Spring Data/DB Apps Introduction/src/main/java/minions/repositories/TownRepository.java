package minions.repositories;

import minions.entities.Town;

import java.util.List;

public interface TownRepository {
    Town findById(int id);

    void save(Town town);

    boolean deleteById(int id);

    List<Town> findAll();

    Town findByName(String townName);

    List<Town> findAllByCountry(String countryName);
}
