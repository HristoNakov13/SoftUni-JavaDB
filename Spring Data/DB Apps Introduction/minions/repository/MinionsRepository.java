package repository;

import entities.Minion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MinionsRepository extends RepositoryImpl<Minion> {
    private static String TABLE_NAME = "minions";

    public MinionsRepository(Connection connection) {
        super(connection);
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
        Minion minion;

        if (townId == 0) {
            minion = new Minion(id, name, age);
        } else {
            minion = new Minion(id, name, age, townId);
        }

        return minion;
    }

    @Override
    public void insert(Minion minion) {
        String townId = String.valueOf(minion.getTownId());
        if (townId.equals("0")) {
            townId = "NULL";
        }

        String queryString ="INSERT INTO " + TABLE_NAME +
                " (`id`, `name`, `age`, `town_id`) VALUES(?, ?, ?, "+ townId +");";

        try {
            PreparedStatement query = this.getConnection().prepareStatement(queryString);
            query.setInt(1, minion.getId());
            query.setString(2, minion.getName());
            query.setInt(3, minion.getAge());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
