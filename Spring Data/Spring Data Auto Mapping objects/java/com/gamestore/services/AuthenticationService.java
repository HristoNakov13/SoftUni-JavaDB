package com.gamestore.services;

public interface AuthenticationService {
    String register(String email, String password, String fullName);

    String login(String email, String password);

    String logout();
}