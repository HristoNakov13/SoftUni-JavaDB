package demo.shop.config;

import demo.shop.domain.entities.Product;
import demo.shop.domain.models.BoughtProductModel;
import demo.shop.domain.models.SellingProductModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    private static ModelMapper modelMapper;
    static {
        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Product.class, SellingProductModel.class)
                .addMapping(src -> {
                    String sellerFirstName = src.getSeller().getFirstName();
                    String sellerLastName = src.getSeller().getLastName();

                    if (sellerFirstName == null) {
                        sellerFirstName = "";
                    } else {
                        sellerFirstName = sellerFirstName + " ";
                    }

                    return sellerFirstName + sellerLastName;
                }, SellingProductModel::setSeller);

        modelMapper.createTypeMap(Product.class, BoughtProductModel.class)
                .addMapping(src -> src.getBuyer().getFirstName(), BoughtProductModel::setBuyerFirstName)
                .addMapping(src -> src.getBuyer().getLastName(), BoughtProductModel::setBuyerLastName);
    }

    @Bean
    public ModelMapper modelMapper() {
        return modelMapper;
    }
}
