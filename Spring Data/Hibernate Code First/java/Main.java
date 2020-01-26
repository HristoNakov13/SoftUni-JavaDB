import entities.gringotts.WizzardDeposit;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
//        #1
//        EntityManager gringottsManager = getEntityManagerForDB("gringotts");
//        WizzardDeposit ivan = new WizzardDeposit("Ivan");
//        ivan.setAge(50);
//        executeTransaction(gringottsManager, () -> gringottsManager.persist(ivan));

//        #2
//        EntityManager storeManager = getEntityManagerForDB("store");

    }
    private static EntityManager getEntityManagerForDB(String dbPersistenceName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(dbPersistenceName);

        return entityManagerFactory.createEntityManager();
    }

    private static void executeTransaction(EntityManager entityManager, Runnable runnable) {
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }
}
