package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import softuni.exam.domain.binding.picture.PictureSeedBindingModel;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.service.CarServiceModel;
import softuni.exam.domain.service.PictureServiceModel;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.parser.dateparser.DateParser;
import softuni.exam.util.reader.FileUtil;
import softuni.exam.util.validator.ValidationUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.util.constants.DateFormats.PICTURES_JSON_DATETIME_FORMAT;
import static softuni.exam.util.constants.FilePaths.PICTURES_FILEPATH;

@Service
public class PictureServiceImpl implements PictureService {

    private PictureRepository pictureRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private DateParser dateParser;
    private Gson gson;
    private CarService carService;

    public PictureServiceImpl(PictureRepository pictureRepository, ValidationUtil validationUtil, ModelMapper modelMapper, FileUtil fileUtil, DateParser dateParser, Gson gson, CarService carService) {
        this.pictureRepository = pictureRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.dateParser = dateParser;
        this.gson = gson;
        this.carService = carService;

        this.init();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return this.fileUtil.getFileContent(PICTURES_FILEPATH);
    }

    @Override
    public String importPictures(String picturesJson) throws IOException {
        PictureSeedBindingModel[] pictures = this.gson.fromJson(picturesJson, PictureSeedBindingModel[].class);
        StringBuilder result = new StringBuilder();

        Arrays.stream(pictures)
                .forEach(picture -> {
                    PictureServiceModel pictureServiceModel = this.modelMapper
                            .map(picture, PictureServiceModel.class);

                    if (this.isValidPicture(pictureServiceModel)) {
                        Picture pictureEntity = this.modelMapper.map(pictureServiceModel, Picture.class);
                        this.pictureRepository.saveAndFlush(pictureEntity);

                        result.append(String.format("Successfully import picture - %s\r\n",
                                picture.getName()));

                        return;
                    }

                    result.append("Invalid picture\r\n");
                });


        return result.toString()
                .trim();
    }

    private boolean isValidPicture(PictureServiceModel pictureServiceModel) {
        boolean hasName = pictureServiceModel.getName() != null;
        boolean isUnique = !hasName
                || this.pictureRepository.findPictureByName(pictureServiceModel.getName()) == null;

        return this.validationUtil.isValid(pictureServiceModel)
                && isUnique;
    }

    private void init() {
        Converter<String, LocalDateTime> stringLocalDateTimeConverter = new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
                return dateParser.fromStringToLocalDateTime(mappingContext.getSource(), PICTURES_JSON_DATETIME_FORMAT);
            }
        };

        Converter<Long, CarServiceModel> carFetcher = new Converter<Long, CarServiceModel>() {
            @Override
            public CarServiceModel convert(MappingContext<Long, CarServiceModel> mappingContext) {
                return carService.getCarById(mappingContext.getSource());
            }
        };

        this.modelMapper.createTypeMap(PictureSeedBindingModel.class, PictureServiceModel.class)
                .addMappings(mapper ->
                        mapper.using(stringLocalDateTimeConverter).map(PictureSeedBindingModel::getDateAndTime, PictureServiceModel::setDateAndTime))
                .addMappings(mapper ->
                        mapper.using(carFetcher).map(PictureSeedBindingModel::getCar, PictureServiceModel::setCar));
    }
}
