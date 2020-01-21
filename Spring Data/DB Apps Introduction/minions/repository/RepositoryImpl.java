package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryImpl<T> implements Repository<T> {
    private Connection connection;

    protected RepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T getById(int id) {
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

    @Override
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

    @Override
    public List<T> getAll() {
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

    protected Connection getConnection() {
        return this.connection;
    }

    protected abstract String getTableName();

    protected abstract T parseRow(ResultSet resultSet) throws SQLException;
}
