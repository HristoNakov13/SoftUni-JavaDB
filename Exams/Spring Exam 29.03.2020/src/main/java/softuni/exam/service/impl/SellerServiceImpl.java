package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.binding.seller.SellerRootSeedBindingModel;
import softuni.exam.domain.binding.seller.SellerSeedBindingModel;
import softuni.exam.domain.entities.Seller;
import softuni.exam.domain.service.SellerServiceModel;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.constants.FilePaths;
import softuni.exam.util.parser.xmlparser.XmlParser;
import softuni.exam.util.reader.FileUtil;
import softuni.exam.util.validator.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static softuni.exam.util.constants.FilePaths.SELLERS_FILEPATH;

@Service
public class SellerServiceImpl implements SellerService {

    private SellerRepository sellerRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private FileUtil fileUtil;
    private XmlParser xmlParser;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil, XmlParser xmlParser) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return this.fileUtil.getFileContent(SELLERS_FILEPATH);
    }

    @Override
    public String importSellers(String sellerXml) throws IOException, JAXBException {
        List<SellerSeedBindingModel> sellers = this.xmlParser
                .parseXml(sellerXml, SellerRootSeedBindingModel.class).getSellers();
        StringBuilder result = new StringBuilder();

        sellers.forEach(seller -> {
            SellerServiceModel sellerServiceModel = this.modelMapper.map(seller, SellerServiceModel.class);

            if (!this.isValidSeller(sellerServiceModel)) {
                result.append("Invalid seller\r\n");

                return;
            }

            Seller sellerEntity = this.modelMapper.map(sellerServiceModel, Seller.class);
            this.sellerRepository.saveAndFlush(sellerEntity);

            result.append(String.format("Successfully import seller %s - %s\r\n",
                    sellerEntity.getLastName(),
                    sellerEntity.getEmail()));
        });

        return result.toString()
                .trim();
    }

    @Override
    public SellerServiceModel getSellerById(Long id) {
        Seller seller = null;

        if (id != null) {
           seller = this.sellerRepository.findById(id)
                   .orElse(null);
        }

        return seller == null
                ? null
                : this.modelMapper.map(seller, SellerServiceModel.class);
    }

    private boolean isValidSeller(SellerServiceModel seller) {
        boolean isUnique = this.sellerRepository
                .findSellerByEmail(seller.getEmail()) == null;

        return this.validationUtil.isValid(seller)
                && isUnique;
    }
}
