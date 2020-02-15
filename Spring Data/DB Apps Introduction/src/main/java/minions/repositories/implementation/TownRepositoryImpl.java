package minions.repositories.implementation;

import minions.entities.Town;
import minions.repositories.TownRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TownRepositoryImpl extends RepositoryImpl<Town> implements TownRepository {
    private final String TABLE_NAME = "towns";

    public TownRepositoryImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return this.TABLE_NAME;
    }

    @Override
    protected Town parseRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");

        Town town = new Town();
        town.setId(id);
        town.setName(name);
        town.setCountry(country);

        return town;
    }

    //if town already exists it gets update otherwise created
    @Override
    public void save(Town town) {
        String queryString = "INSERT INTO towns(name, country) " +
                "VALUES(?, ?)";

        Town townExists = this.findById(town.getId());
        if (townExists != null) {
            queryString = "UPDATE towns SET name = ?, country = ? WHERE id = " + townExists.getId();
        }

        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(queryString);
            preparedStatement.setString(1, town.getName());
            preparedStatement.setString(2, town.getCountry());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Town> findAllByCountry(String countryName) {
        String queryString = "SELECT * " +
                "FROM towns " +
                "WHERE country = ?";

        List<Town> towns = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(queryString);
            preparedStatement.setString(1, countryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                towns.add(this.parseRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return towns;
    }
}
