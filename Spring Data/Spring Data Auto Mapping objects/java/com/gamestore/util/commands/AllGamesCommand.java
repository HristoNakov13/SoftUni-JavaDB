package com.gamestore.util.commands;

import com.gamestore.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

@Cmd
public class AllGamesCommand implements Command {
    private GameService gameService;

    public AllGamesCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(String... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
        return this.gameService.getTitlesOfAllGames();
    }
}
