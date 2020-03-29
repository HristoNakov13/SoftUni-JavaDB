package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.domain.entities.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findSellerByEmail(String email);
}
