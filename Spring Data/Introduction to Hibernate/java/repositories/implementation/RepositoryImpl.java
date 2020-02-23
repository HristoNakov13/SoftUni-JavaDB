package repositories.implementation;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
        String queryString = "SELECT t FROM " + this.getEntityName() + " t";

        return this.entityManager
                .createQuery(queryString, this.getEntityClass())
                .getResultList();
    }

    protected void deleteById(int id) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.remove(this.findById(id));
            this.entityManager.getTransaction().commit();
        } catch (IllegalArgumentException ignored) {
        }
    }

    //if entity already exists its updated otherwise created
    protected void save(T entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(entity);
        this.entityManager.getTransaction().commit();
    }

    //creates/updates entity and returns it with the applied changes at the end
    protected T saveAndFlush(T entity) {
        this.save(entity);

        String queryString;
        Integer id = this.getEntityId(entity);

        if (id == null) {
            queryString = "SELECT t FROM " + this.getEntityName() + " t ORDER BY t.id DESC";
        } else {
            queryString = "SELECT t FROM " + this.getEntityName() + " t WHERE t.id = " + id;
        }

        return this.entityManager
                .createQuery(queryString, this.getEntityClass())
                .setMaxResults(1)
                .getSingleResult();
    }

    //Extracts the ID of an entity. If the entity has no ID returns null.
    private Integer getEntityId(T entity) {
        Method idGetMethod = Arrays
                .stream(this.getEntityClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        try {
            idGetMethod.setAccessible(true);
            return (Integer) idGetMethod.invoke(entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    private Class<T> getEntityClass() {
        return this.clazz;
    }

    private String getEntityName() {
        return this.clazz.getSimpleName();
    }
}
