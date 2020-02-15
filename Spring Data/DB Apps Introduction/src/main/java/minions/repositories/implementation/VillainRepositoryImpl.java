package minions.repositories.implementation;


import minions.entities.Minion;
import minions.entities.Villain;
import minions.repositories.MinionRepository;
import minions.repositories.VillainRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VillainRepositoryImpl extends RepositoryImpl<Villain> implements VillainRepository {
    private MinionRepository minionsRepository;
    private static String TABLE_NAME = "villains";

    public VillainRepositoryImpl(Connection connection, MinionRepository minionRepository) {
        super(connection);
        this.minionsRepository = minionRepository;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    protected Villain parseRow(ResultSet resultSet) {
        Villain villain = null;

        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String evilnessFactor = resultSet.getString("evilness_factor");
            villain = new Villain();

            villain.setId(id);
            villain.setName(name);
            villain.setEvilnessFactor(evilnessFactor);
            List<Minion> villainMinions = this.getVillainMinions(id);
            villain.setMinions(villainMinions);
        } catch (SQLException ignored) {
        }

        return villain;
    }

    public void save(Villain villain) {
        String queryString = String.format("INSERT INTO %s (name, evilness_factor) VALUES('%s', '%s')",
                TABLE_NAME,
                villain.getName(),
                villain.getEvilnessFactor());

        try {
            PreparedStatement query = super.getConnection().prepareStatement(queryString);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getMinionIds(int villainId) {
        String queryString = "SELECT vm.minion_id" +
                "\nFROM villains v" +
                "\nINNER JOIN minions_villains vm" +
                "\nON v.id = vm.villain_id" +
                "\nWHERE v.id = ?";

        List<Integer> ids = new ArrayList<>();
        try {
            PreparedStatement query = this.getConnection().prepareStatement(queryString);
            query.setInt(1, villainId);
            ResultSet resultSet = query.executeQuery();

            while (resultSet.next()) {
                ids.add(resultSet.getInt("minion_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids;
    }

    private List<Minion> getVillainMinions(int villainId) {
        List<Minion> minions = new ArrayList<>();
        List<Integer> minionIds = getMinionIds(villainId);
        minionIds.forEach(id -> minions.add(this.minionsRepository.findById(id)));

        return minions;
    }

    @Override
    public Villain findById(int villainId) {
        return super.findById(villainId);
    }

    @Override
    public List<Villain> findAll() {
        List<Villain> villains = super.findAll();
        villains.forEach(villain -> villain.setMinions(this.getVillainMinions(villain.getId())));

        return villains;
    }

    @Override
    public List<Villain> findAllVillainsByMinionsGreaterThan(int greaterThan) {
        String queryString = "SELECT v.id, v.name, v.evilness_factor " +
                "FROM villains v " +
                "INNER JOIN minions_villains mv " +
                "ON mv.villain_id = v.id " +
                "GROUP BY v.id " +
                "HAVING COUNT(mv.minion_id) > ? " +
                "ORDER BY COUNT(mv.minion_id) DESC";

        List<Villain> villains = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(queryString);
            preparedStatement.setInt(1, greaterThan);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Villain villain = this.parseRow(resultSet);
                villains.add(villain);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return villains;
    }

    @Override
    public void addMinionToVillain(Villain villain, Minion minion) {
        String queryString = "INSERT INTO minions_villains(minion_id, villain_id) " +
                "VALUES(?, ?)";
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(queryString);
            preparedStatement.setInt(1, minion.getId());
            preparedStatement.setInt(2, villain.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public Villain findByName(String villainName) {
//        String queryString = "SELECT * " +
//                "FROM villains " +
//                "WHERE name = ? " +
//                "LIMIT 1;";
//        Villain villain = null;
//
//        try {
//            PreparedStatement preparedStatement = this.getConnection().prepareStatement(queryString);
//            preparedStatement.setString(1, villainName);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//
//            villain = this.parseRow(resultSet);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return villain;
//    }
}
