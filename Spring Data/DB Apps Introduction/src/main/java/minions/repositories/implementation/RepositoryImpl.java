package minions.repositories.implementation;

import minions.entities.Town;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryImpl<T>{
    private Connection connection;

    protected RepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    public T findById(int id) {
        String queryString = "SELECT * " +
                "FROM " + this.getTableName() +
                "\nWHERE id = " + id;
        T result = null;

        try {
            PreparedStatement query = this.connection.prepareStatement(queryString);
//            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            resultSet.next();
            result = this.parseRow(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deleteById(int id) {
        String queryString = "DELETE FROM " + this.getTableName() +
                "\nWHERE id = " + id;

        try {
            PreparedStatement query = this.connection.prepareStatement(queryString);
            query.executeUpdate();

            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public List<T> findAll() {
        String queryString = "SELECT * FROM " + this.getTableName();
        List<T> result = new ArrayList<>();
        try {
            PreparedStatement query = this.connection.prepareStatement(queryString);
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                result.add(parseRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public T findByName(String name) {
        String queryString = "SELECT * FROM " + this.getTableName()
                + " WHERE name = '" + name + "'";

        T entity = null;

        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(queryString);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                entity = this.parseRow(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    protected Connection getConnection() {
        return this.connection;
    }

    protected abstract String getTableName();

    protected abstract T parseRow(ResultSet resultSet) throws SQLException;
}
