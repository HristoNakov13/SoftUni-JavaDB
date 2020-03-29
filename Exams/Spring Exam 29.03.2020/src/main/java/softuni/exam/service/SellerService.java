package softuni.exam.service;

import softuni.exam.domain.service.SellerServiceModel;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SellerService {
    
    boolean areImported();

    String readSellersFromFile() throws IOException;

    String importSellers(String sellersXml) throws IOException, JAXBException;

    SellerServiceModel getSellerById(Long id);

}
