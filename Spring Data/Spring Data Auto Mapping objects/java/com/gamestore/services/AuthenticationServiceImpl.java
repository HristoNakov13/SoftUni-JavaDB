package com.gamestore.services;


import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Override
    public String register(String email, String password, String fullName) {
        return null;
    }

    @Override
    public String login(String email, String password) {
        return null;
    }

    @Override
    public String logout() {
        return null;
    }
}
