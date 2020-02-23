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
    public Town saveAndFlush(Town town) {
        return super.saveAndFlush(town);
    }

    @Override
    public List<Town> findByNameShorterThan(int charsCount) {
        String queryString = "SELECT t FROM Town t WHERE LENGTH(t.name) < :charsCount";

        return super.getEntityManager()
                .createQuery(queryString, Town.class)
                .setParameter("charsCount", charsCount)
                .getResultList();
    }

    @Override
    public Town findByName(String townName) {
        String queryString = "SELECT t FROM Town t WHERE t.name = :townName";

        return super.getEntityManager().createQuery(queryString, Town.class)
                .setParameter("townName", townName)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(int id) {
        super.deleteById(id);
    }
}