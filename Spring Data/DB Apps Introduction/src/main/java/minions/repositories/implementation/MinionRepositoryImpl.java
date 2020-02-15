package minions.repositories.implementation;


import minions.entities.Minion;
import minions.repositories.MinionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MinionRepositoryImpl extends RepositoryImpl<Minion> implements MinionRepository {
    private static String TABLE_NAME = "minions";

    public MinionRepositoryImpl(Connection connection) {
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
