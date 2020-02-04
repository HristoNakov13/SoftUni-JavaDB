package com.gamestore.services.exceptions;

public class InvalidUserCredentials extends Exception {
    public InvalidUserCredentials(String message) {
        super(message);
    }
}
