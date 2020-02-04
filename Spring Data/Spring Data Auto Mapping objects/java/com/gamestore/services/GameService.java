package com.gamestore.services;

import com.gamestore.domain.entities.Game;
import com.gamestore.domain.models.GameCreateModel;
import com.gamestore.repositories.GameRepository;
import com.gamestore.services.messages.GameMessages;
import com.gamestore.services.session.UserSession;
import com.gamestore.services.validators.GameValidator;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class GameService {
    private GameRepository gameRepository;
    private ModelMapper modelMapper;
    private UserSession userSession;

    public GameService(GameRepository gameRepository, UserSession userSession) {
        this.gameRepository = gameRepository;
        this.userSession = userSession;
        this.modelMapper = new ModelMapper();
    }

    //7 params way too much. Is it better to accept String[] as sole argument then?
    public String addGame(String[] args) {
        if (!this.userSession.hasUserLogged()) {
            return GameMessages.NO_LOGGED_USER;
        }

        if(!this.userSession.isAdmin()) {
            return GameMessages.UNAUTHORIZED;
        }

        String validatorMessage = GameValidator.validateGame(args);

        if (!validatorMessage.equals(GameMessages.VALID_GAME)) {
            return validatorMessage;
        }

        double price = Double.parseDouble(args[1]);
        double size = Double.parseDouble(args[2]);
        String trailerID = extractYoutubeVideoID(args[3]);
        Date releaseDate = Date.valueOf(args[6]);

        GameCreateModel gameCreateModel = new GameCreateModel(args[0], price, size, trailerID, args[4], args[5], releaseDate);
        Game game = this.modelMapper.map(gameCreateModel, Game.class);

        this.gameRepository.save(game);

        return String.format(GameMessages.SUCC_ADDED_GAME, gameCreateModel.getTitle());
    }

    private String extractYoutubeVideoID(String videoURL) {
        return videoURL.substring(videoURL.length() - 11);
    }

    public String editGame(String[] args) {
//        TODO
        return null;
    }

    public String deleteGame(String id) {
        int gameId;
        try {
            gameId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return GameMessages.DELETE_INVALID_ID;
        }

        try {
            this.gameRepository.deleteById(gameId);
        } catch (EmptyResultDataAccessException e) {
            return GameMessages.DELETE_ID_NOT_EXIST;
        }

        return GameMessages.DELETE_SUCCESS;
    }
}