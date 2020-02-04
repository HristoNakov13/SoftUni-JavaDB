package com.gamestore.repositories;

import com.gamestore.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmailAndPassword(String email, String password);

    User findUserByEmail(String email);
}
