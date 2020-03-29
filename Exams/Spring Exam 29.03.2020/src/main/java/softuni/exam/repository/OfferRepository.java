package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.domain.entities.Offer;

import java.time.LocalDateTime;


public interface OfferRepository extends JpaRepository<Offer, Long> {

    Offer findOfferByDescriptionAndAddedOn(String description, LocalDateTime addedOn);
    
}
