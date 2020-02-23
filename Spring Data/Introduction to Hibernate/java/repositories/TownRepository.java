package repositories;

import entities.Town;

import java.util.List;

public interface TownRepository {
    Town findById(int id);

    List<Town> findAll();

    void save(Town town);

    Town saveAndFlush(Town town);

    List<Town> findByNameShorterThan(int charsCount);

    Town findByName(String townName);

    void deleteById(int id);
}
