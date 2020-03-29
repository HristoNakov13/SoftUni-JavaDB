package softuni.exam.service.impl;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import softuni.exam.domain.binding.offer.CarElementSeedModel;
import softuni.exam.domain.binding.offer.OfferRootSeedBindingModel;
import softuni.exam.domain.binding.offer.OfferSeedBindingModel;
import softuni.exam.domain.binding.offer.SellerElementSeedModel;
import softuni.exam.domain.entities.Offer;
import softuni.exam.domain.service.CarServiceModel;
import softuni.exam.domain.service.OfferServiceModel;
import softuni.exam.domain.service.SellerServiceModel;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.constants.DateFormats;
import softuni.exam.util.parser.dateparser.DateParser;
import softuni.exam.util.parser.xmlparser.XmlParser;
import softuni.exam.util.reader.FileUtil;
import softuni.exam.util.validator.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static softuni.exam.util.constants.DateFormats.OFFERS_XML_DATETIME_FORMAT;
import static softuni.exam.util.constants.FilePaths.OFFERS_FILEPATH;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private XmlParser xmlParser;
    private DateParser dateParser;
    private ValidationUtil validationUtil;
    private CarService carService;
    private SellerService sellerService;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, FileUtil fileUtil, XmlParser xmlParser, DateParser dateParser, ValidationUtil validationUtil, CarService carService, SellerService sellerService) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.dateParser = dateParser;
        this.validationUtil = validationUtil;
        this.carService = carService;
        this.sellerService = sellerService;

        this.init();
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return this.fileUtil.getFileContent(OFFERS_FILEPATH);
    }

    @Override
    public String importOffers(String offersXml) throws IOException, JAXBException {
        List<OfferSeedBindingModel> offers = this.xmlParser
                .parseXml(offersXml, OfferRootSeedBindingModel.class).getOffers();
        StringBuilder result = new StringBuilder();

        offers.forEach(offer -> {
            OfferServiceModel offerServiceModel = this.modelMapper.map(offer, OfferServiceModel.class);

            if (!this.isValidOffer(offerServiceModel)) {
                result.append("Invalid offer\r\n");

                return;
            }

            Offer offerEntity = this.modelMapper.map(offerServiceModel, Offer.class);
            this.offerRepository.saveAndFlush(offerEntity);

            result.append(String.format("Successfully import offer %s - %s\r\n",
                    offerEntity.getAddedOn(),
                    offerEntity.isHasGoldStatus()));
        });

        return result.toString()
                .trim();
    }

    private boolean isValidOffer(OfferServiceModel offerServiceModel) {
        boolean isUnique = this.offerRepository
                .findOfferByDescriptionAndAddedOn(offerServiceModel.getDescription(), offerServiceModel.getAddedOn())
                == null;

        return this.validationUtil.isValid(offerServiceModel) && isUnique;
    }

    private void init() {
        Converter<CarElementSeedModel, CarServiceModel> carFetch = new Converter<CarElementSeedModel, CarServiceModel>() {
            @Override
            public CarServiceModel convert(MappingContext<CarElementSeedModel, CarServiceModel> mappingContext) {
                return carService.getCarById(mappingContext.getSource().getId());
            }
        };

        Converter<SellerElementSeedModel, SellerServiceModel> sellerFetch = new Converter<SellerElementSeedModel, SellerServiceModel>() {
            @Override
            public SellerServiceModel convert(MappingContext<SellerElementSeedModel, SellerServiceModel> mappingContext) {
                return sellerService.getSellerById(mappingContext.getSource().getId());
            }
        };

        Converter<String, LocalDateTime> stringLocalDateTimeConverter = new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
                return dateParser.fromStringToLocalDateTime(mappingContext.getSource(), OFFERS_XML_DATETIME_FORMAT);
            }
        };

        this.modelMapper.createTypeMap(OfferSeedBindingModel.class, OfferServiceModel.class)
                .addMappings(mapper ->
                        mapper.using(carFetch).map(OfferSeedBindingModel::getCar, OfferServiceModel::setCar))
                .addMappings(mapper ->
                        mapper.using(sellerFetch).map(OfferSeedBindingModel::getSeller, OfferServiceModel::setSeller))
                .addMappings(mapper ->
                        mapper.using(stringLocalDateTimeConverter).map(OfferSeedBindingModel::getAddedOn, OfferServiceModel::setAddedOn));
    }
}
