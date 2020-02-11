package demo.shop.repositories;

import demo.shop.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u INNER JOIN Product p ON u.id = p.seller.id WHERE p.buyer IS NOT NULL")
    List<User> getAllUsersWithSales();
}
