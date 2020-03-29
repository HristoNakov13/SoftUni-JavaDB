package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.exam.domain.entities.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    Car findCarByMakeAndModelAndKilometers(String make, String model, Integer kilometers);

    @Query("SELECT c FROM Car c " +
            "ORDER BY c.pictures.size DESC, c.make")
    List<Car> findAllCarsByPicturesCountOrderByCountAndMake();
}
