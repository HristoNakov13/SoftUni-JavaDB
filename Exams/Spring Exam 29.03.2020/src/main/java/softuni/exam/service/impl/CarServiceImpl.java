package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import softuni.exam.domain.binding.car.CarSeedBindingModel;
import softuni.exam.domain.entities.Car;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.models.CarExportViewModel;
import softuni.exam.domain.service.CarServiceModel;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.parser.dateparser.DateParser;
import softuni.exam.util.reader.FileUtil;
import softuni.exam.util.validator.ValidationUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.util.constants.DateFormats.CARS_JSON_DATE_FORMAT;
import static softuni.exam.util.constants.FilePaths.CARS_FILEPATH;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private FileUtil fileUtil;
    private ValidationUtil validationUtil;
    private DateParser dateParser;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil, DateParser dateParser) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.dateParser = dateParser;

        this.init();
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return this.fileUtil.getFileContent(CARS_FILEPATH);
    }

    @Override
    public String importCars(String carsJson) throws IOException {
        CarSeedBindingModel[] cars = this.gson.fromJson(carsJson, CarSeedBindingModel[].class);
        StringBuilder result = new StringBuilder();

        Arrays.stream(cars)
                .forEach(car -> {
                    CarServiceModel carServiceModel = this.modelMapper
                            .map(car, CarServiceModel.class);

                    if (this.isValidCar(carServiceModel)) {
                        Car carEntity = this.modelMapper.map(carServiceModel, Car.class);
                        this.carRepository.saveAndFlush(carEntity);

                        result.append(String.format("Successfully imported - %s - %s", car.getMake(), car.getModel()))
                                .append(System.lineSeparator());

                        return;
                    }

                    result.append("Invalid car\r\n");
                });

        return result.toString()
                .trim();
    }

    private boolean isValidCar(CarServiceModel car) {
        return this.validationUtil.isValid(car)
                && this.isUniqueCar(car.getMake(), car.getModel(), car.getKilometers());
    }

    private boolean isUniqueCar(String make, String model, Integer kilometers) {
        return this.carRepository.findCarByMakeAndModelAndKilometers(make, model, kilometers) == null;
    }

    //out of time to pass as view model to controller at this point
    @Override
    @Transactional
    public String getCarsOrderByPicturesCountThenByMake() {
        String format = "Car make - %s, model - %s\r\n\tKilometers - %s\r\n\tRegistered on - %s\r\n\tNumber of pictures - %s\r\n\t";

        return this.carRepository
                .findAllCarsByPicturesCountOrderByCountAndMake()
                .stream()
                .map(car -> this.modelMapper.map(car, CarExportViewModel.class))
                .map(car -> String.format(format, car.getModel(),
                        car.getModel(),
                        car.getKilometers(),
                        car.getRegisteredOn(),
                        car.getPicturesCount()))
                .collect(Collectors.joining("\r\n"));
    }

    @Override
    public CarServiceModel getCarById(Long id) {
        Car car = this.carRepository.findById(id)
                .orElse(null);

        return car == null
                ? null
                : this.modelMapper.map(car, CarServiceModel.class);
    }

    private void init() {
        //seed to service
        Converter<String, LocalDate> stringLocalDateConverter = new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
                return dateParser.fromStringToLocalDate(mappingContext.getSource(), CARS_JSON_DATE_FORMAT);
            }
        };

        this.modelMapper.createTypeMap(CarSeedBindingModel.class, CarServiceModel.class)
                .addMappings(mapper ->
                        mapper.using(stringLocalDateConverter).map(CarSeedBindingModel::getRegisteredOn, CarServiceModel::setRegisteredOn));

        //entity to view
        Converter<Set<Picture>, Integer> picturesCountConverter = new Converter<Set<Picture>, Integer>() {
            @Override
            public Integer convert(MappingContext<Set<Picture>, Integer> mappingContext) {
                return mappingContext.getSource() == null
                        ? 0
                        : mappingContext.getSource().size();
            }
        };

        this.modelMapper.createTypeMap(Car.class, CarExportViewModel.class)
                .addMappings(mapper ->
                        mapper.using(picturesCountConverter).map(Car::getPictures, CarExportViewModel::setPicturesCount));
    }
}
