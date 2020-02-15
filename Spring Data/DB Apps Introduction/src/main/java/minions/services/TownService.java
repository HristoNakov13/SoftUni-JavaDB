package minions.services;

import minions.entities.Town;
import minions.repositories.TownRepository;
import minions.repositories.implementation.TownRepositoryImpl;
import minions.util.DatabaseConnector;

import java.sql.Connection;
import java.util.List;

public class TownService {
    private DatabaseConnector databaseConnector;
    private TownRepository townRepository;

    public TownService(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.initRepositories();
    }

    public String changeTownCasingsByCountry(String countryName) {
        List<Town> towns = this.townRepository.findAllByCountry(countryName);

        if (towns.isEmpty()) {
            return "No town names were affected.";
        }

        StringBuilder result = new StringBuilder();
        result.append(towns.size())
                .append(" town names were affected. ")
                .append(System.lineSeparator())
                .append("[");

        towns.forEach(town -> {
            town.setName(town.getName().toUpperCase());
            result.append(town.getName()).append(", ");
            this.townRepository.save(town);
        });
        result.replace(result.length() - 2, result.length(), "")
                .append("]");

        return result.toString();
    }

    private void initRepositories() {
        Connection connection = this.databaseConnector.getConnection();
        this.townRepository = new TownRepositoryImpl(connection);
    }
}
