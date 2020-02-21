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

        //#1
        // CONFIGURATION
        String DB_USERNAME = "root";
        String DB_PASSWORD = "1234";
        String DB_URL = "jdbc:mysql://localhost:3306/";
        String MINIONS_DB_NAME = "minions_db";

        // ---------------------------- CONFIGURATION

        //INIT

        DatabaseConnector databaseConnector = new DatabaseConnector(DB_USERNAME, DB_PASSWORD, MINIONS_DB_NAME, DB_URL);
        databaseConnector.setupConnection();

        VillainService villainService = new VillainService(databaseConnector);
        TownService townService = new TownService(databaseConnector);
        MinionService minionService = new MinionService(databaseConnector);

        //------------------------- INIT


//        #2
//        int invalidCount = 66;
//        int minionsCount = 15;
//        System.out.println(villainService.getVillainAndMinionNamesByMinionCount(minionsCount));
//        System.out.println("----------------------------------");
//        System.out.println(villainService.getVillainAndMinionNamesByMinionCount(invalidCount));
//
//
////        #3
//        int villainId = 2;
//        int invalidVillainId = 55;
//        System.out.println(villainService.getVillainNameAndMinionsById(villainId));
//                System.out.println("----------------------------------");
//        System.out.println(villainService.getVillainNameAndMinionsById(invalidVillainId));
//
//
////        #4
////        way too lazy to implement terminal at this point
////        no input validation so its up to you to work with correct data and format
//
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
//
////        #5
//        String countryName = "Bulgaria";
//        String invalidCountryName = "asdqe";
//
//        System.out.println(townService.changeTownCasingsByCountry(countryName));
//        System.out.println("----------------------------------");
//        System.out.println(townService.changeTownCasingsByCountry(invalidCountryName));
//
//
////        #6
//        int releaseVillainId = 3;
//        int invalidReleaseId = 1000;
//        System.out.println(villainService.deleteByIdAndReleaseMinions(releaseVillainId));
//        System.out.println("----------------------------------");
//        System.out.println(villainService.deleteByIdAndReleaseMinions(invalidReleaseId));
//
//
////        #7
//        System.out.println(minionService.getAllMinionNamesFirstLastShuffle());
//
////        #8
//        Scanner sc = new Scanner(System.in);
//        int[] minionIds = Arrays.stream(sc.nextLine().split(" "))
//                .mapToInt(Integer::parseInt)
//                .toArray();
//
//        System.out.println(minionService.increaseAgeAndLowerCaseNames(minionIds));
//
////        #9
//        int minionIdAge = 3;
//        int invalidMinionId = 587;
//
//        System.out.println(minionService.increaseAgeOFMinionById(minionIdAge));
//        System.out.println("----------------------------------");
//        System.out.println(minionService.increaseAgeOFMinionById(invalidMinionId));
    }
}
