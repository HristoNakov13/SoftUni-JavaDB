package com.gamestore.services.session;

import com.gamestore.domain.models.GameCartModel;
import com.gamestore.domain.models.UserModel;

import java.util.Set;

public interface UserSession {
    boolean hasUserLogged();

    boolean isAdmin();

    UserModel getUser();

    void logout();

    void login(UserModel user);

    void addToCart(GameCartModel game);

    boolean removeFromCart(String title);

    Set<GameCartModel> getCart();

    boolean cartContainsGame(String title);

    public void clearCart();
}
