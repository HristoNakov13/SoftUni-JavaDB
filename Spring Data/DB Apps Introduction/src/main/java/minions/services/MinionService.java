package minions.services;

import minions.entities.Minion;
import minions.repositories.MinionRepository;
import minions.repositories.implementation.MinionRepositoryImpl;
import minions.util.DatabaseConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinionService {
    private DatabaseConnector databaseConnector;
    private MinionRepository minionRepository;

    public MinionService(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.initRepositories();
    }

    public String getAllMinionNamesFirstLastShuffle() {
        List<Minion> minions = this.minionRepository.findAll();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < minions.size() / 2; i++) {
            result.append(minions.get(i).getName())
                    .append(System.lineSeparator())
                    .append(minions.get(minions.size() - 1 - i).getName())
                    .append(System.lineSeparator());
        }

        if (minions.size() % 2 != 0) {
            result.append(minions.get(minions.size() / 2).getName());
        }

        return result.toString().trim();
    }

    public String increaseAgeAndLowerCaseNames(int[] minionIds) {
        List<Minion> editMinions = new ArrayList<>();
        Arrays.stream(minionIds).forEach(id -> editMinions.add(this.minionRepository.findById(id)));

        editMinions.forEach(minion -> {
            minion.setAge(minion.getAge() + 1);
            minion.setName(minion.getName().toLowerCase());
            this.minionRepository.save(minion);
        });

        List<Minion> allMinions = this.minionRepository.findAll();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < allMinions.size(); i++) {
            result.append(String.format("%s %s %s",
                        i + 1,
                        allMinions.get(i).getName(),
                        allMinions.get(i).getAge()))
                    .append(System.lineSeparator());
        }

        return result.toString().trim();
    }

    public String increaseAgeOFMinionById(int minionId) {
        this.minionRepository.callGetOlderProcedure(minionId);
        Minion minion = this.minionRepository.findById(minionId);

        if (minion == null) {
            return String.format("No such minion with ID: %s", minionId);
        }

        return String.format("Name: %s, Age: %s",
                minion.getName(),
                minion.getAge());
    }

    private DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }

    private void initRepositories() {
        this.minionRepository = new MinionRepositoryImpl(this.databaseConnector.getConnection());
    }
}