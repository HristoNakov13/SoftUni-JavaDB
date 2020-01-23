package orm;

import annotations.Column;
import annotations.Entity;
import annotations.PrimaryKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbManager<T> implements DbContext<T> {
    private static final String SELECT_ALL = "SELECT * FROM {0}";
    private static final String SELECT_FIRST = SELECT_ALL + " {1}" + " LIMIT 1";
    private static final String INSERT = "INSERT INTO {0} ({1}) VALUES({2})";
    private static final String DELETE = "DELETE FROM {0} WHERE {1}";
    private Connection connection;
    private String tableName;
    private Class<T> clazz;

    public DbManager(Connection connection, Class<T> clazz) {
        this.connection = connection;
        this.clazz = clazz;
        this.setTableName(clazz);
    }

    public boolean insert(T entity) throws SQLException {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        Arrays.stream(this.clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    columns.append(field.getAnnotation(Column.class).name())
                            .append(", ");
                    try {
                        values.append("'").append(field.get(entity)).append("', ");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        columns.delete(columns.length() - 2, columns.length());
        values.delete(values.length() - 2, values.length());

        String queryString = MessageFormat.format(INSERT, this.getTableName(), columns, values);
        return connection.prepareStatement(queryString).execute();
    }

    public void deleteById(int id) throws SQLException {
        Field primaryKey = this.getPrimaryKey();
        primaryKey.setAccessible(true);
        String where = primaryKey.getAnnotation(Column.class).name() + " = " + id;
        String query = MessageFormat.format(DELETE, this.getTableName(), where);

        this.connection.prepareStatement(query).execute();
    }

    public Iterable<T> getAll() throws SQLException {
        return this.getAll("");
    }

    public Iterable<T> getAll(String where) throws SQLException {
        String queryString = MessageFormat.format(SELECT_ALL + " " + where, this.getTableName());
        PreparedStatement query = this.connection.prepareStatement(queryString);
        ResultSet resultSet = query.executeQuery();
        List<T> result = new ArrayList<>();

        while (resultSet.next()) {
            T entity = mapEntity(resultSet);
            result.add(entity);
        }

        return result;
    }

    public T findById(int id) throws SQLException {
        Field primaryKey = getPrimaryKey();
        String idColumnName = primaryKey.getAnnotation(Column.class).name();
        String where = "WHERE " + idColumnName + " = " + id;

        return findFirst(where);
    }

    public T findFirst() throws SQLException {
        return findFirst("");
    }

    public T findFirst(String where) throws SQLException {
        String queryString = MessageFormat.format(SELECT_FIRST, this.getTableName(), where);
        PreparedStatement query = this.connection.prepareStatement(queryString);
        ResultSet resultSet = query.executeQuery();
        resultSet.next();

        return mapEntity(resultSet);
    }

    private String getTableName() {
        return this.tableName;
    }

    //Trash hardcoded to work with only 3 data types

    private T mapEntity(ResultSet resultSet) {
        T entity = null;

        try {
            entity = this.clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();

        }

        T finalEntity = entity;
        Arrays.stream(this.clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        String columnName = field.getAnnotation(Column.class).name();
                        if (field.getType() == String.class) {
                            String value = resultSet.getString(columnName);
                            field.set(finalEntity, value);

                        } else if (field.getType() == Date.class) {
                            Date value = resultSet.getDate(columnName);
                            field.set(finalEntity, value);
                        } else {
                            int value = resultSet.getInt(columnName);
                            field.set(finalEntity, value);
                        }
                    } catch (SQLException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        return entity;
    }

    private Field getPrimaryKey() {
        return Arrays.stream(this.clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(PrimaryKey.class))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private void setTableName(Class<T> clazz) {
        Annotation annotation = Arrays.stream(clazz.getAnnotations())
                .filter(a -> a.annotationType() == Entity.class)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        Entity entityAnnotation = (Entity) annotation;

        this.tableName = entityAnnotation.name();
    }
}