package com.gamestore.services.commands;

import org.springframework.stereotype.Component;

@Component
public interface Command {
    String execute(String... args);
}