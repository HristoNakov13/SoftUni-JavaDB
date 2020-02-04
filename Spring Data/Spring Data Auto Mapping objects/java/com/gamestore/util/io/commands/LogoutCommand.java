package com.gamestore.util.io.commands;

import com.gamestore.services.AuthenticationService;
import com.gamestore.services.AuthenticationServiceImpl;

@Cmd
public class LogoutCommand implements Command {
    private AuthenticationService authenticationService;

    public LogoutCommand(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public String execute(String... args) {
        return this.authenticationService.logout();
    }
}
