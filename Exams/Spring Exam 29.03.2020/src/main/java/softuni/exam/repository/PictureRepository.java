package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.domain.entities.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    Picture findPictureByName(String name);
}
