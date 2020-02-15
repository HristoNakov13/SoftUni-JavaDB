package minions;

import minions.services.TownService;
import minions.services.VillainService;
import minions.util.DatabaseConnector;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        String DB_USERNAME = "root";
        String DB_PASSWORD = "1234";
        String DB_URL = "jdbc:mysql://localhost:3306/";
        String MINIONS_DB_NAME = "minions_db";

        DatabaseConnector databaseConnector = new DatabaseConnector(DB_USERNAME, DB_PASSWORD, MINIONS_DB_NAME, DB_URL);
        databaseConnector.setupConnection();


        VillainService villainService = new VillainService(databaseConnector);
        TownService townService = new TownService(databaseConnector);

        //#2
//        int invalidCount = 66;
//        int minionsCount = 15;
//        System.out.println(villainService.getVillainAndMinionNamesByMinionCount(minionsCount));

        //#3
//        int villainId = 3;
//        int invalidVillainId = 55;
//        System.out.println(villainService.getVillainNameAndMinionsById(villainId));

        //#4
        //way too lazy to implement terminal at this point
        //no input validation so its up to you to work with correct data and format

//        Scanner scanner = new Scanner(System.in);
//        String[] minionData = scanner.nextLine()
//                .replace("Minion: ", "")
//                .split(" ");
//
//        String villainName = scanner
//                .nextLine()
//                .replace("Villain: ", "");
//
//        System.out.println(villainService.addMinionToVillain(minionData, villainName));

        //#5
        String countryName = "Bulgaria";
        System.out.println(townService.changeTownCasingsByCountry(countryName));
    }
}
