package softuni.exam.domain.models.binding.picture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListPictureCreateBindingModel {

    @XmlElement(name = "picture")
    private List<PictureCreateBindingModel> pictures;

    public List<PictureCreateBindingModel> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureCreateBindingModel> pictures) {
        this.pictures = pictures;
    }
}
