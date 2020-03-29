package softuni.exam.service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

public interface PlayerService {
    String importPlayers(String playersJson) throws IOException;

    boolean areImported();

    String readPlayersJsonFile() throws IOException;

    String exportPlayersInATeam(String teamName);

    String exportPlayersWhereSalaryBiggerThan(BigDecimal salary);
}
