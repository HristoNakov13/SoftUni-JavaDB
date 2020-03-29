package softuni.exam.service;

import softuni.exam.domain.service.CarServiceModel;

import java.io.IOException;

public interface CarService {

    boolean areImported();

    String readCarsFileContent() throws IOException;
	
	String importCars(String carsJson) throws IOException;

    String getCarsOrderByPicturesCountThenByMake();

    CarServiceModel getCarById(Long id);
}
