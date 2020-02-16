package minions.repositories;

import minions.entities.Minion;

import java.util.List;

public interface MinionRepository {
    Minion findById(int id);

    void save(Minion minion);

    boolean deleteById(int id);

    List<Minion> findAll();

    Minion findByName(String minionName);

    void callGetOlderProcedure(int minionId);
}