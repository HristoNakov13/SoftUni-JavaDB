package repository;

import java.util.List;

public interface Repository<T> {
    T getById(int id);

    void insert(T obj);

    boolean deleteById(int id);

    List<T> getAll();
}
