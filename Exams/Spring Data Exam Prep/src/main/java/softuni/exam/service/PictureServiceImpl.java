package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.models.binding.picture.ListPictureCreateBindingModel;
import softuni.exam.domain.models.binding.picture.PictureCreateBindingModel;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.constants.FilePaths;
import softuni.exam.util.parser.XmlParser;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private PictureRepository pictureRepository;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;
    private ValidatorUtil validator;

    public PictureServiceImpl(PictureRepository pictureRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validator) {
        this.pictureRepository = pictureRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public String importPictures() throws IOException, JAXBException {
        StringBuilder result = new StringBuilder();

        List<PictureCreateBindingModel> pictures = this.xmlParser
                .fromXmlToObject(FilePaths.PICTURES_XML, ListPictureCreateBindingModel.class)
                .getPictures();

        List<Picture> pictureEntities = pictures
                .stream()
                .filter(picture -> {
                    if (this.validator.isValid(picture)) {
                        result.append(String.format("Successfully imported picture - %s\r\n", picture.getUrl()));

                        return true;
                    }
                    result.append("Invalid picture\r\n");

                    return false;
                })
                .map(picture -> this.modelMapper.map(picture, Picture.class))
                .collect(Collectors.toList());

        this.pictureRepository.saveAll(pictureEntities);

        return result.toString()
                .trim();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return Files.readString(Paths.get(FilePaths.PICTURES_XML));
    }

    @Override
    public Picture getPictureByUrl(String url) {
        return this.pictureRepository.findPictureByUrl(url);
    }
}
