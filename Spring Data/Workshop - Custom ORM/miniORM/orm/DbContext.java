package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DbContext<T> {
    boolean insert(T obj) throws SQLException;

    void deleteById(int id) throws SQLException;

    Iterable<T> getAll() throws SQLException;

    Iterable<T> getAll(String where) throws SQLException;

    T findFirst() throws SQLException;

    T findFirst(String where) throws SQLException;
}
