package repositories.implementation;

import entities.Town;
import repositories.TownRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class TownRepositoryImpl extends RepositoryImpl<Town> implements TownRepository {

    public TownRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Town.class);
    }

    @Override
    public Town findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<Town> findAll() {
        return super.findAll();
    }

    @Override
    public void save(Town town) {
        super.save(town);
    }

    @Override
    public List<Town> findByNameShorterThan(int charsCount) {
        String queryString = "SELECT t FROM Town t WHERE LENGTH(t.name) < :charsCount";

        return super.getEntityManager()
                .createQuery(queryString, Town.class)
                .setParameter("charsCount", charsCount)
                .getResultList();
    }
}