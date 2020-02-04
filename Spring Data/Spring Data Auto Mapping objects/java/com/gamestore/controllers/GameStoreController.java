package com.gamestore.controllers;

import com.gamestore.util.io.InputHandler;
import com.gamestore.util.io.InputHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class GameStoreController implements CommandLineRunner {
    private final InputHandler inputHandler;

    public GameStoreController(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public void run(String... args) throws Exception {

//        implements console login/reg/logout service
        Scanner scanner = new Scanner(System.in);
        String commandLine;

        while (!(commandLine = scanner.nextLine()).equals("END")) {
            System.out.println(this.inputHandler.executeInput(commandLine));
        }
    }
}
