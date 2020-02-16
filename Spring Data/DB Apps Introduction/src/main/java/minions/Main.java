package minions;

import minions.services.MinionService;
import minions.services.TownService;
import minions.services.VillainService;
import minions.util.DatabaseConnector;

import java.sql.*;
import java.util.Arrays;
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
        MinionService minionService = new MinionService(databaseConnector);

        //#2
//        int invalidCount = 66;
//        int minionsCount = 2;
//        System.out.println(villainService.getVillainAndMinionNamesByMinionCount(minionsCount));
//        System.out.println(villainService.getVillainAndMinionNamesByMinionCount(invalidCount));
//        System.out.println("----------------------------------");
//
////        #3
//        int villainId = 4;
//        int invalidVillainId = 55;
//        System.out.println(villainService.getVillainNameAndMinionsById(villainId));
//        System.out.println(villainService.getVillainNameAndMinionsById(invalidVillainId));
//        System.out.println("----------------------------------");

////        #4
//        way too lazy to implement terminal at this point
//        no input validation so its up to you to work with correct data and format

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
//        System.out.println("----------------------------------");
//
////        #5
//        String countryName = "Bulgaria";
//        System.out.println(townService.changeTownCasingsByCountry(countryName));
//        System.out.println("----------------------------------");
//
////        #6
//        int releaseVillainId = 3;
//        int invalidReleaseId = 1000;
//        System.out.println(villainService.deleteByIdAndReleaseMinions(releaseVillainId));
//        System.out.println(villainService.deleteByIdAndReleaseMinions(invalidReleaseId));
//        System.out.println("----------------------------------");
//
////        #7
//        System.out.println(minionService.getAllMinionNamesFirstLastShuffle());
//        System.out.println("----------------------------------");
//
////        #8
//        Scanner sc = new Scanner(System.in);
//        int[] minionIds = Arrays.stream(sc.nextLine().split(" "))
//                .mapToInt(Integer::parseInt)
//                .toArray();
//
//        System.out.println(minionService.increaseAgeAndLowerCaseNames(minionIds));
//        System.out.println("----------------------------------");
//
////        #9
//        int minionIdAge = 5;
//        int invalidMinionId = 587;
//
//        System.out.println(minionService.increaseAgeOFMinionById(minionIdAge));
//        System.out.println(minionService.increaseAgeOFMinionById(invalidMinionId));
    }
}
