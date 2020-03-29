package softuni.exam.service;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.domain.models.binding.picture.PictureCreateBindingModel;
import softuni.exam.domain.models.binding.player.PlayerCreateBindingModel;
import softuni.exam.domain.models.binding.team.TeamCreateBindingModel;
import softuni.exam.domain.view.PlayerPlayingForViewModel;
import softuni.exam.domain.view.PlayerSalaryViewModel;
import softuni.exam.domain.view.TeamPlayersViewModel;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.constants.FilePaths;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private TeamService teamService;
    private PictureService pictureService;
    private ModelMapper modelMapper;
    private ValidatorUtil validator;
    private Gson gson;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamService teamService, ModelMapper modelMapper, ValidatorUtil validator, Gson gson, PictureService pictureService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public String importPlayers(String playersJson) throws IOException {
        PlayerCreateBindingModel[] playersArray = this.gson.fromJson(playersJson, PlayerCreateBindingModel[].class);
        StringBuilder result = new StringBuilder();

        List<Player> players = Arrays.stream(playersArray)
                .filter(player -> {
                    if (player.getPicture() != null && player.getTeam() != null) {
                        player.setPicture(this.modelMapper.map(this.pictureService.getPictureByUrl(player.getPicture().getUrl()), PictureCreateBindingModel.class));
                        player.setTeam(this.modelMapper.map(this.teamService.getTeamByName(player.getTeam().getName()), TeamCreateBindingModel.class));
                    }

                    if (this.validator.isValid(player)) {
                        result.append(String.format("Successfully imported player: %s %s\r\n", player.getFirstName(), player.getLastName()));

                        return true;
                    }
                    result.append("Invalid player\r\n");

                    return false;
                }).map(currentPlayer -> this.modelMapper.map(currentPlayer, Player.class))
                .collect(Collectors.toList());


        this.playerRepository.saveAll(players);

        return result.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        String path = FilePaths.PLAYERS_JSON;
        return Files.readString(Paths.get(FilePaths.PLAYERS_JSON));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan(BigDecimal salary) {
        List<PlayerSalaryViewModel> players = this.playerRepository
                .findAllBySalaryGreaterThanOrderBySalaryDesc(salary)
                .stream()
                .map(player -> this.modelMapper.map(player, PlayerSalaryViewModel.class))
                .collect(Collectors.toList());

        String format = "Player name: %s %s\r\n\tNumber: %s\r\n\tSalary: %s\r\n\tTeam: %s";

        return players.stream()
                .map(player ->
                        String.format(format,
                                player.getFirstName(),
                                player.getLastName(),
                                player.getNumber(),
                                player.getSalary(),
                                player.getTeam() != null ? player.getTeam().getName() : "No team."))
                .collect(Collectors.joining("\r\n"));
    }

    @Override
    public String exportPlayersInATeam(String teamName) {
        Team teamEntity = this.teamService.getTeamByName(teamName);

        if (teamEntity == null) {
            return "Invalid team.";
        }

        TeamPlayersViewModel team = this.modelMapper.map(teamEntity, TeamPlayersViewModel.class);
        String format = "Player name: %s %s - %s\r\nNumber: %s";

        String playersInfo = team.getPlayers()
                .stream()
                .sorted(Comparator.comparing(PlayerPlayingForViewModel::getId))
                .map(player ->
                        String.format(format,
                                player.getFirstName(),
                                player.getLastName(),
                                player.getPosition().name(),
                                player.getNumber()))
                .collect(Collectors.joining("\r\n"));

        return String.format("Team: %s\r\n%s",
                team.getName(),
                playersInfo);
    }
}
