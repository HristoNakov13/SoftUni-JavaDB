import entities.gringotts.WizzardDeposit;
import entities.university.Course;
import entities.university.Student;

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

//        #3
//        EntityManager universityManager = getEntityManagerForDB("university");

//        #4
//        EntityManager hospitalManager = getEntityManagerForDB("hospital");
//        #5
//        EntityManager billingManager = getEntityManagerForDB("bill_system");


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