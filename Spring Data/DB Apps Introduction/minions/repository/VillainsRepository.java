package repository;

import entities.Minion;
import entities.Villain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VillainsRepository extends RepositoryImpl<Villain> {
    private Repository<Minion> minionsRepository;
    private static String TABLE_NAME = "villains";

    public VillainsRepository(Connection connection, Repository<Minion> minionRepository) {
        super(connection);
        this.minionsRepository = minionRepository;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Villain parseRow(ResultSet resultSet) throws SQLException {
        Villain villain;
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String evilnessFactor = resultSet.getString("evilness_factor");

        if (evilnessFactor == null) {
            villain = new Villain(id, name);
        } else {
            villain = new Villain(id, name, evilnessFactor);
        }

        return villain;
    }

    @Override
    public void insert(Villain villain) {
        String queryString = String.format("INSERT INTO %s (id, name, evilness_factor) VALUES(%s, %s, %s)",
                TABLE_NAME,
                villain.getId(),
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

    private void fillMinions(Villain villain, List<Integer> minionIds) {
        List<Minion> minions = new ArrayList<>();
        minionIds.forEach(id -> minions.add(this.minionsRepository.getById(id)));

        minions.forEach(villain::addMinion);
    }

    @Override
    public Villain getById(int villainId) {
        Villain villain = super.getById(villainId);
        List<Integer> minionIds = getMinionIds(villain.getId());
        fillMinions(villain, minionIds);

        return villain;
    }

    @Override
    public List<Villain> getAll() {
        List<Villain> villains = super.getAll();
        villains.forEach(villain -> {
            List<Integer> minionIds = getMinionIds(villain.getId());
            fillMinions(villain, minionIds);
        });

        return villains;
    }
}
