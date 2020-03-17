package com.gamestore.services;

import com.gamestore.domain.entities.enums.UserType;
import com.gamestore.domain.models.UserCreateModel;
import com.gamestore.domain.models.UserModel;
import com.gamestore.util.exceptions.InvalidUserCredentials;
import com.gamestore.util.messages.AuthMessages;
import com.gamestore.services.session.UserSession;
import com.gamestore.services.validators.RegisterValidator;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    private UserService userService;
    private UserSession userSession;

    public AuthenticationServiceImpl(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
    }

//    Too many if statements.
//    Cant think of a better implementation other than throwing exceptions in RegValidator.
    @Override
    public String register(String email, String password, String confirmPassword, String fullName) {
        if (this.userService.isRegisteredUser(email)) {
            return AuthMessages.EMAIL_INUSE;
        }

        try {
            RegisterValidator.validateCredentials(email, fullName, password, confirmPassword);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        UserType userType = this.userService.isFirstToRegister()
                ? UserType.ADMINISTRATOR
                : UserType.BASIC;

        UserCreateModel userCreateModel = new UserCreateModel(fullName, email, password, userType);
        this.userService.registerUser(userCreateModel);

        return String.format(AuthMessages.REGISTER_SUCCESS, fullName);
    }

    @Override
    public String login(String email, String password) {
        if (this.userSession.hasUserLogged()) {
            return AuthMessages.USER_ALREADY_LOGGED;
        }

        UserModel user;
        try {
           user = this.userService.getUserByEmailPassword(email, password);
        }catch (InvalidUserCredentials e) {
            return e.getMessage();
        }

        this.userSession.login(user);

        return String.format(AuthMessages.LOGIN_SUCCESS, user.getFullName());
    }

    @Override
    public String logout() {
        if (this.userSession.hasUserLogged()) {
            String fullName = this.userSession.getUser().getFullName();
            this.userSession.logout();

            return String.format(AuthMessages.LOGOUT_SUCCESS, fullName);
        }

        return AuthMessages.LOGOUT_NO_USER_LOGGED;
    }
}
