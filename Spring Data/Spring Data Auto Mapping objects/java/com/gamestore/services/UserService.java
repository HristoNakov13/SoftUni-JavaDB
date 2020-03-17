package com.gamestore.services;

import com.gamestore.domain.entities.Game;
import com.gamestore.domain.entities.User;
import com.gamestore.domain.models.UserBuyerModel;
import com.gamestore.domain.models.UserCreateModel;
import com.gamestore.domain.models.UserModel;
import com.gamestore.repositories.UserRepository;
import com.gamestore.services.session.UserSession;
import com.gamestore.util.exceptions.InvalidUserCredentials;
import com.gamestore.util.messages.AuthMessages;
import com.gamestore.util.messages.UserControlMessages;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserSession userSession;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, UserSession userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
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

    @Transactional
    public UserBuyerModel getBuyerById(Integer id) {
        return this.modelMapper.map(this.userRepository.findById(id).get(), UserBuyerModel.class);
    }

    public void buyGames(UserBuyerModel buyer) {
        User user = this.userRepository.findById(buyer.getId()).get();
        User buyerEntity = this.modelMapper.map(buyer, User.class);

        user.setGames(buyerEntity.getGames());
        user.setOrders(buyerEntity.getOrders());
        this.userRepository.save(user);
    }

    @Transactional
    public String getOwnedGamesTitles() {
        if (!this.userSession.hasUserLogged()) {
            return UserControlMessages.NO_LOGGED_USER;
        }

        User user = this.userRepository.findById(this.userSession.getUser().getId()).get();
        String gamesList = user.getGames()
                .stream()
                .map(Game::getTitle)
                .collect(Collectors.joining("\r\n"));

        return user.getGames().isEmpty()
                ? UserControlMessages.EMPTY_GAMES_COLLECTION
                : gamesList;
    }
}