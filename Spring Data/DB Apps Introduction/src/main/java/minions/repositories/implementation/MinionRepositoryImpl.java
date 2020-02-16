package minions.repositories.implementation;


import minions.entities.Minion;
import minions.repositories.MinionRepository;

import java.sql.*;

public class MinionRepositoryImpl extends RepositoryImpl<Minion> implements MinionRepository {
    private static String TABLE_NAME = "minions";

    public MinionRepositoryImpl(Connection connection) {
        super(connection);
        this.initStoredProcedures();
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Minion parseRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        int townId = resultSet.getInt("town_id");

        Minion minion = new Minion();
        minion.setId(id);
        minion.setName(name);
        minion.setAge(age);
        minion.setTownId(townId);

        return minion;
    }

    @Override
    public void save(Minion minion) {
        String townId = String.valueOf(minion.getTownId());


        String queryString = "INSERT INTO " + TABLE_NAME +
                " (name, age, town_id) VALUES(?, ?, ?);";

        Minion minionExists = this.findById(minion.getId());

        if (minionExists != null) {
            queryString = "UPDATE " + TABLE_NAME +
                    " SET name = ?, age = ?, town_id = ? " +
                    " WHERE id = " + minion.getId();
        }


        try {
            PreparedStatement query = this.getConnection().prepareStatement(queryString);
            query.setString(1, minion.getName());
            query.setInt(2, minion.getAge());
            query.setInt(3, minion.getTownId());

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void callGetOlderProcedure(int minionId) {
        String queryString = "CALL usp_get_older(?)";

        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(queryString);
            preparedStatement.setInt(1, minionId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initStoredProcedures() {
        String queryString = "CREATE PROCEDURE usp_get_older(minion_id INT) " +
                "BEGIN " +
                "UPDATE " + TABLE_NAME +
                " SET age = age + 1 " +
                "WHERE id = minion_id; " +
                "END";

        try {
            PreparedStatement statement = this.getConnection().prepareStatement(queryString);
            statement.execute();
        } catch (SQLException ignored) {
        }
    }
}
