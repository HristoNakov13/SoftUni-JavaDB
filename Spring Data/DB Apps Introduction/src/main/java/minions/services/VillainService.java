package minions.services;

import minions.entities.Minion;
import minions.entities.Town;
import minions.entities.Villain;
import minions.repositories.MinionRepository;
import minions.repositories.TownRepository;
import minions.repositories.VillainRepository;
import minions.repositories.implementation.MinionRepositoryImpl;
import minions.repositories.implementation.TownRepositoryImpl;
import minions.repositories.implementation.VillainRepositoryImpl;
import minions.util.DatabaseConnector;

import java.sql.Connection;
import java.util.List;

public class VillainService {
    private VillainRepository villainRepository;
    private TownRepository townRepository;
    private MinionRepository minionRepository;
    private DatabaseConnector databaseConnector;

    public VillainService(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.initRepositories();
    }

    public String getVillainAndMinionNamesByMinionCount(int minionCount) {
        List<Villain> villains = this.villainRepository.findAllVillainsByMinionsGreaterThan(minionCount);
        if (villains.isEmpty()) {
            return "No minions found.";
        }

        StringBuilder villainsInfo = new StringBuilder();

        villains.forEach(villain ->
                villainsInfo.append(villain.getName())
                        .append(" ")
                        .append(villain.getMinions().size())
                        .append(System.lineSeparator()));

        return villainsInfo.toString().trim();
    }

    public String getVillainNameAndMinionsById(int villainId) {
        Villain villain = this.villainRepository.findById(villainId);

        if (villain == null) {
            return String.format("No villain with ID %d exists in the database.", villainId);
        }

        StringBuilder villainsInfo = new StringBuilder();
        villainsInfo.append("Villain: ")
                .append(villain.getName())
                .append(System.lineSeparator());

        int counter = 1;
        for(Minion minion : villain.getMinions()) {
            villainsInfo
                    .append(String.format("%s. ", counter++))
                    .append(minion.getName())
                    .append(" ")
                    .append(minion.getAge())
                    .append(System.lineSeparator());
        }

        return villainsInfo.toString().trim();
    }

    public String addMinionToVillain(String[] minionData, String villainName) {
        String DEFAULT_EVILNESS_FACTOR = "evil";
        String DEFAULT_COUNTRY = "Bulgaria";
        String result = "";

        Villain villain = this.villainRepository.findByName(villainName);

        if(villain == null) {
            villain = new Villain();
            villain.setName(villainName);
            villain.setEvilnessFactor(DEFAULT_EVILNESS_FACTOR);
            this.villainRepository.save(villain);
            villain = this.villainRepository.findByName(villainName);

            result += String.format("Villain %s was added to the database.%s", villainName, System.lineSeparator());
        }

        String minionName = minionData[0];
        int minionAge = Integer.parseInt(minionData[1]);
        String townName = minionData[2];

        Town town  = this.townRepository.findByName(townName);

        //lol no country is specified in the input. Default is set as Bulgaria
        if (town == null) {
            town = new Town();
            town.setName(townName);
            town.setCountry(DEFAULT_COUNTRY);
            this.townRepository.save(town);

            //fetching it again so I can access the assigned ID
            town = this.townRepository.findByName(townName);

            result += String.format("Town %s was added to the database.%s", townName, System.lineSeparator());
        }


        Minion minion = new Minion();
        minion.setName(minionName);
        minion.setAge(minionAge);
        minion.setTownId(town.getId());
        this.minionRepository.save(minion);

        //fetching it again so I can access the assigned ID
        minion = this.minionRepository.findByName(minionName);

        this.villainRepository.addMinionToVillain(villain, minion);
        result += String.format("Successfully added %s to be minion of %s.", minion.getName(), villain.getName());

        return result;
    }

    public String deleteByIdAndReleaseMinions(int villainId) {
        Villain villain = this.villainRepository.findById(villainId);

        if (villain == null) {
            return "No such villain was found";
        }

        this.villainRepository.deleteByIdAndReleaseMinions(villainId);

        return String.format("%s was deleted\r\n%s minions released",
                villain.getName(),
                villain.getMinions().size());
    }

    public void saveVillainToDb(Villain villain) {
        this.villainRepository.save(villain);
    }

    private void initRepositories() {
        Connection connection = this.getDatabaseConnector().getConnection();

        this.minionRepository = new MinionRepositoryImpl(connection);
        this.townRepository = new TownRepositoryImpl(connection);
        this.villainRepository = new VillainRepositoryImpl(connection, this.minionRepository);
    }

    private DatabaseConnector getDatabaseConnector() {
        return this.databaseConnector;
    }
}
