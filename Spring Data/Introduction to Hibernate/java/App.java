import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Town> query = builder.createQuery(Town.class);
        query.from(Town.class);

        List<Town> towns = entityManager.createQuery(query).getResultList();
        towns.stream()
                .filter(town -> town.getName().length() > 5)
                .forEach(entityManager::detach);
        towns.forEach(town -> {
            town.setName(town.getName().toLowerCase());
            if (entityManager.contains(town)) {
                entityManager.persist(town);
            }
        });

        List<Town> updatedTowns = entityManager.createQuery(query).getResultList();
        updatedTowns.forEach(town -> System.out.println(town.getName()));

    }
}
