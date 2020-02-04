package com.gamestore.services.session;

import com.gamestore.domain.models.UserModel;

public interface UserSession {
    boolean hasUserLogged();

    boolean isAdmin();

    UserModel getUser();

    void logout();

    void login(UserModel user);
}
