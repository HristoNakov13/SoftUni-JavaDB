package com.gamestore.util.io.commands;

import com.gamestore.services.GameService;

@Cmd
public class EditGameCommand implements Command {
    private GameService gameService;

    public EditGameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(String... args) {
        return this.gameService.editGame(args);
    }
}
