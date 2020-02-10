package demo.shop.services;

import demo.shop.domain.entities.Product;
import demo.shop.domain.models.createmodels.ProductCreateModel;
import demo.shop.repositories.ProductRepository;
import demo.shop.services.validators.ProductValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    private ProductValidator productValidator;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.productValidator = productValidator;
    }

    public void saveToDb(ProductCreateModel productCreateModel) {
        if (!this.productValidator.isValidProduct(productCreateModel)) {
            return;
        }

        Product product = this.modelMapper.map(productCreateModel, Product.class);

        this.productRepository.save(product);
    }

    public void saveAllToDb(List<ProductCreateModel> products) {
        products.stream()
                .filter(this.productValidator::isValidProduct)
                .map(product -> this.modelMapper.map(product, Product.class))
                .forEach(this.productRepository::save);
    }
}
