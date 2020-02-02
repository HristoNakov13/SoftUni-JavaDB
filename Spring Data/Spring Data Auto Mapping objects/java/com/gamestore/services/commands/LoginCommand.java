package com.gamestore.services.commands;

import com.gamestore.services.AuthenticationService;
import com.gamestore.services.AuthenticationServiceImpl;
import org.springframework.stereotype.Component;

@Component
@Cmd
public class LoginCommand implements Command {
    private AuthenticationService authenticationService;

    public LoginCommand(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public String execute(String... args) {
        return this.authenticationService.login(args[0], args[1]);
    }
}