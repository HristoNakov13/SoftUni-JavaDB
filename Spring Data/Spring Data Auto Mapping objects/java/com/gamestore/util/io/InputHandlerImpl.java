package com.gamestore.util.io;

import com.gamestore.services.AuthenticationService;
import com.gamestore.services.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Component
public class InputHandlerImpl implements InputHandler {
    private final String COMMANDS_PACKAGE_NAME = "com.gamestore.services.commands";
    private Map<String, Command> commands;
    private final AuthenticationService authenticationService;

    @Autowired
    public InputHandlerImpl(AuthenticationService authenticationService) throws IOException, ClassNotFoundException {
        this.authenticationService = authenticationService;
        this.initHandler();
    }

    //Extract command classes then their names
    //Adds to commands map corresponding Command class instance with command name

    @PostConstruct
    private void initHandler() throws IOException, ClassNotFoundException {
        this.commands = new HashMap<>();
        List<Class> commandClasses = PackageClassFinder.getCommandClasses(COMMANDS_PACKAGE_NAME);

        commandClasses.forEach(commandClazz -> {
            try {
                String commandName = commandClazz.getSimpleName().replace("Command", "");
                Command command = (Command) commandClazz.getConstructor(this.authenticationService.getClass())
                        .newInstance(this.authenticationService);

                commands.put(commandName, command);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    private String getCommandName(String input) {
        return input.split("\\|")[0].replace("User", "");
    }

    private String[] getCommandArgs(String input) {
        String[] commandLine = input.split("\\|");
        return Arrays.copyOfRange(commandLine, 1, commandLine.length);
    }

    @Override
    public String executeInput(String input) {
        String commandName = this.getCommandName(input);

        if (!isValidCommand(commandName)) {
            return "Invalid command!";
        }

        String[] commandArgs = this.getCommandArgs(input);

        return this.commands.get(commandName).execute(commandArgs);
    }

    private boolean isValidCommand(String commandName) {
        return this.commands.containsKey(commandName);
    }
}