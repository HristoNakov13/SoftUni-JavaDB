package com.gamestore.services.session;

import com.gamestore.domain.entities.User;
import com.gamestore.domain.entities.enums.UserType;
import com.gamestore.domain.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserSessionimpl implements UserSession {
    private UserModel currentUser;

    public UserSessionimpl() {
    }

    @Override
    public boolean hasUserLogged() {
        return this.currentUser != null;
    }

    @Override
    public boolean isAdmin() {
        return this.getUser().getUserType().name().equals(UserType.ADMINISTRATOR.name());
    }

    @Override
    public UserModel getUser() {
        return this.currentUser;
    }

    @Override
    public void logout() {
        this.currentUser = null;
    }

    @Override
    public void login(UserModel user) {
        this.currentUser = user;
    }
}
