package softuni.exam.domain.binding.offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferRootSeedBindingModel {

    @XmlElement(name = "offer")
    private List<OfferSeedBindingModel> offers;

    public OfferRootSeedBindingModel() {
    }

    public List<OfferSeedBindingModel> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferSeedBindingModel> offers) {
        this.offers = offers;
    }
}
