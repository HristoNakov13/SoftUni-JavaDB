package demo.shop.config;

import demo.shop.domain.entities.Product;
import demo.shop.domain.entities.User;
import demo.shop.domain.models.plainmodels.productsmodels.BoughtProductModel;
import demo.shop.domain.models.plainmodels.productsmodels.SellerProductModel;
import demo.shop.domain.models.plainmodels.usersmodels.UserWithSoldProductsModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    private static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();

        Converter<Product, String> firstNameLastNameCombine = new Converter<>() {
            @Override
            public String convert(MappingContext<Product, String> context) {
                return context.getSource().getSeller().getFirstName() + " " + context.getSource().getSeller().getLastName();
            }
        };

        //without converter modelmapper throws java.lang.IllegalArgumentException: object is not an instance of declaring class

        modelMapper.createTypeMap(Product.class, SellerProductModel.class)
                .addMappings(new PropertyMap<Product, SellerProductModel>() {
                    @Override
                    protected void configure() {
                        using(firstNameLastNameCombine).map(source).setSeller("");
                    }
                });

        modelMapper.createTypeMap(User.class, UserWithSoldProductsModel.class)
                .addMapping(User::getSellingProducts, UserWithSoldProductsModel::setSoldProducts);

        modelMapper.createTypeMap(Product.class, BoughtProductModel.class)
                .addMapping(src -> src.getBuyer().getFirstName(), BoughtProductModel::setBuyerFirstName)
                .addMapping(src -> src.getBuyer().getLastName(), BoughtProductModel::setBuyerLastName);

        modelMapper.validate();
    }

    @Bean
    public ModelMapper modelMapper() {
        return modelMapper;
    }
}
