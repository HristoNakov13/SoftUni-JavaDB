package com.gamestore.util.io.commands;

import com.gamestore.services.GameService;

@Cmd
public class AddGameCommand implements Command {
    private GameService gameService;

    //hardcoded to hell but fug it. Console app a shit anyway
    private static int NUMBER_OF_NEEDED_ARGS = 7;

    public AddGameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(String... args) {
        if (args.length != NUMBER_OF_NEEDED_ARGS) {
            return "Fill all fields to add game.";
        }

        return this.gameService.addGame(args);
    }
}
