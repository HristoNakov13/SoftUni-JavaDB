package minions.repositories;

import minions.entities.Minion;
import minions.entities.Villain;

import java.util.List;

public interface VillainRepository {
    Villain findById(int id);

    void save(Villain villain);

    boolean deleteById(int id);

    List<Villain> findAll();

    List<Villain> findAllVillainsByMinionsGreaterThan(int greaterThan);

    Villain findByName(String villainName);

    void addMinionToVillain(Villain villain, Minion minion);
}