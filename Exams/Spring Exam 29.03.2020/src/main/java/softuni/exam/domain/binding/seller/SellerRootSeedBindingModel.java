package softuni.exam.domain.binding.seller;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerRootSeedBindingModel {

    @XmlElement(name = "seller")
    private List<SellerSeedBindingModel> sellers;

    public SellerRootSeedBindingModel() {
    }

    public List<SellerSeedBindingModel> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerSeedBindingModel> sellers) {
        this.sellers = sellers;
    }
}
