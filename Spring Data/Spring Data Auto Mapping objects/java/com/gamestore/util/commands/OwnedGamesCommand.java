package com.gamestore.util.commands;

import com.gamestore.services.GameService;
import com.gamestore.services.UserService;

@Cmd
public class OwnedGamesCommand implements Command {
    private GameService gameService;

    public OwnedGamesCommand(GameService userService) {
        this.gameService = userService;
    }

    @Override
    public String execute(String... args) {
        return this.gameService.getOwnedGamesTitles();
    }
}
