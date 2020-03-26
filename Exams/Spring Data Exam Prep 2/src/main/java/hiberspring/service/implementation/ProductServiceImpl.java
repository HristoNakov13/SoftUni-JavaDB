package hiberspring.service.implementation;

import hiberspring.common.Constants;
import hiberspring.domain.dtos.ProductRootSeedDto;
import hiberspring.domain.dtos.ProductSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.ProductRepository;
import hiberspring.service.BranchService;
import hiberspring.service.ProductService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCTS_FILENAME = "products.xml";

    private ProductRepository productRepository;
    private BranchService branchService;
    private ModelMapper modelMapper;
    private XmlParser xmlParser;
    private FileUtil fileUtil;
    private ValidationUtil validator;

    public ProductServiceImpl(ProductRepository productRepository, BranchService branchService, ModelMapper modelMapper, XmlParser xmlParser, FileUtil fileUtil, ValidationUtil validator) {
        this.productRepository = productRepository;
        this.branchService = branchService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.validator = validator;

        this.init();
    }

    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return this.fileUtil.readFile(Constants.PATH_TO_FILES + PRODUCTS_FILENAME);
    }

    @Override
    public String importProducts() throws JAXBException, IOException {
        List<ProductSeedDto> products = this.xmlParser
                .parseXml(ProductRootSeedDto.class, Constants.PATH_TO_FILES + PRODUCTS_FILENAME)
                .getProducts();
        StringBuilder result = new StringBuilder();

        List<Product> productEntities = products.stream()
                .filter(product -> {
                    if (this.validator.isValid(product) && this.branchService.getBranchByName(product.getBranch()) != null) {
                        result.append(
                                String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                                        product.getClass().getSimpleName(),
                                        product.getName()))
                                .append(System.lineSeparator());

                        return true;
                    }
                    result.append(Constants.INCORRECT_DATA_MESSAGE)
                            .append(System.lineSeparator());

                    return false;
                })
                .map(product -> this.modelMapper.map(product, Product.class))
                .collect(Collectors.toList());

        this.productRepository.saveAll(productEntities);

        return result.toString()
                .trim();
    }

    private void init() {
        Converter<String, Branch> branchFetchConverter = new Converter<String, Branch>() {
            @Override
            public Branch convert(MappingContext<String, Branch> mappingContext) {
                return branchService.getBranchByName(mappingContext.getSource());
            }
        };

        this.modelMapper.createTypeMap(ProductSeedDto.class, Product.class)
                .addMappings(mapper ->
                        mapper.using(branchFetchConverter).map(ProductSeedDto::getBranch, Product::setBranch));
    }
}
