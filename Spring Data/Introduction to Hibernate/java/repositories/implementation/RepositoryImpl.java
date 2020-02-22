package repositories.implementation;

import javax.persistence.EntityManager;
import java.util.List;

abstract class RepositoryImpl<T> {
    private EntityManager entityManager;
    private Class<T> clazz;

    protected RepositoryImpl(EntityManager entityManager, Class<T> clazz) {
        this.entityManager = entityManager;
        this.clazz = clazz;
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }


    protected T findById(int id) {
        return this.entityManager.find(this.getEntityClass(), id);
    }

    protected List<T> findAll() {
        return this.entityManager
                .createQuery("SELECT t FROM " + this.getEntityName() + " t")
                .getResultList();
    }

    //if entity already exists its updated otherwise created
    protected void save(T entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(entity);
        this.entityManager.getTransaction().commit();
    }

    private Class<T> getEntityClass() {
        return this.clazz;
    }

    private String getEntityName() {
        return this.clazz.getSimpleName();
    }
}
