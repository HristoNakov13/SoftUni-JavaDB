package services;

import entities.Town;
import repositories.TownRepository;
import repositories.implementation.TownRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class TownService {
    private TownRepository townRepository;

    public TownService(EntityManager entityManager) {
        this.townRepository = new TownRepositoryImpl(entityManager);
    }

    /**
     * The task was intended to begin a transaction and inside it to detach those towns with names longer than 5 chars.
     * After that set the names of the still attached towns to lower case.
     * Example:
     * em.getTransaction().begin();
     * townsWithLongerNames.detach();
     * stillAttached.setName(nameToLowerCase);
     * em.getTransaction().commit();
     * Thus eliminating the need to persist each individually since its done automatically at the end of the transaction.
     * My implementation with service/repository layer accepts an entity and creates/updates it.
     * If it exists its updated otherwise created.
     */

    public String setNamesToLowerCaseByNameShorterThan(int charsCount) {
        List<Town> towns = this.townRepository.findByNameShorterThan(charsCount);

        //lowers name to lowercase and saves the updated town to the db
        return towns.stream()
                .map(town -> {
                    town.setName(town.getName().toLowerCase());
                    this.townRepository.save(town);
                    return town.getName();
                }).collect(Collectors.joining("\r\n"));
    }
}
