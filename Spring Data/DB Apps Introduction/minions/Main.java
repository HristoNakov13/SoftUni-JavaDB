import entities.Minion;
import entities.Villain;
import repository.MinionsRepository;
import repository.Repository;
import repository.VillainsRepository;

import java.util.List;
import java.util.Properties;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String DB_USERNAME = "root";
        String DB_PASSWORD = "1234";
        String DB_URL = "jdbc:mysql://localhost:3306/";

        String MINIONS_DB_NAME = "minions_db";

        Properties props = new Properties();
        props.setProperty("user", DB_USERNAME);
        props.setProperty("password", DB_PASSWORD);

        Connection minionsConnection = DriverManager.getConnection(DB_URL + MINIONS_DB_NAME, props);

        Repository<Minion> minionRepository = new MinionsRepository(minionsConnection);
        Repository<Villain> villainRepository = new VillainsRepository(minionsConnection, minionRepository);

//        ------2------
        int filterVillainsMinionCount = 3;


        List<Villain> villains = villainRepository.getAll();
        villains.stream()
                .filter(villain -> villain.getMinions().size() > filterVillainsMinionCount)
                .sorted((v1, v2) -> v2.getMinions().size() - v1.getMinions().size())
                .forEach(villain -> System.out.println(String.format("Name: %s, Minions count: %s", villain.getName(), villain.getMinions().size())));

//        ------3------

        int testVillainID = 1;
        Villain carl = villainRepository.getById(testVillainID);
        List<Minion> carlsMinions = carl.getMinions();
        System.out.println("Villain: " + carl.getName());

        for (int i = 0; i < carlsMinions.size(); i++) {
            Minion currentMinion = carlsMinions.get(i);
            System.out.println(String.format("%d. %s %d", i + 1, currentMinion.getName(), currentMinion.getAge()));
        }
    }
}
