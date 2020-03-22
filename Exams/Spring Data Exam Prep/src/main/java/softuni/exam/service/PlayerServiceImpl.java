package softuni.exam.service;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.models.binding.player.PlayerCreateBindingModel;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.constants.FilePaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private ModelMapper modelMapper;
    private ValidatorUtil validator;
    private Gson gson;

    public PlayerServiceImpl(PlayerRepository playerRepository, PictureService pictureService, TeamService teamService, ModelMapper modelMapper, ValidatorUtil validator, Gson gson) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public String importPlayers() throws IOException {
        PlayerCreateBindingModel[] playersArray = this.gson.fromJson(this.readPlayersJsonFile(), PlayerCreateBindingModel[].class);

        List<Player> players = Arrays.stream(playersArray)
                .filter(this.validator::isValid)
                .map(player -> this.modelMapper.map(player, Player.class))
                .collect(Collectors.toList());

        this.playerRepository.saveAll(players);

        return "";
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Paths.get(FilePaths.PLAYERS_JSON));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        return "";
    }

    @Override
    public String exportPlayersInATeam() {
        return "";
    }
}
