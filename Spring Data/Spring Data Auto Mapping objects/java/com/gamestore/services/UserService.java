package com.gamestore.services;

import com.gamestore.domain.entities.User;
import com.gamestore.domain.models.UserCreateModel;
import com.gamestore.domain.models.UserModel;
import com.gamestore.repositories.UserRepository;
import com.gamestore.services.exceptions.InvalidUserCredentials;
import com.gamestore.services.messages.AuthMessages;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    public boolean isRegisteredUser(String email) {
        User user = this.userRepository.findUserByEmail(email);

        return user != null;
    }

    public UserModel getUserByEmailPassword(String email, String password) throws InvalidUserCredentials {
        User user = this.userRepository.findUserByEmailAndPassword(email, password);

        if (user == null) {
            throw new InvalidUserCredentials(AuthMessages.LOGIN_WRONG_CREDENTIALS);
        }

        return this.modelMapper.map(user, UserModel.class);
    }

    public void registerUser(UserCreateModel userCreateModel) {
        User user = this.modelMapper.map(userCreateModel, User.class);

        this.userRepository.save(user);
    }

    public boolean isFirstToRegister() {
        return this.userRepository.findAll().isEmpty();
    }
}