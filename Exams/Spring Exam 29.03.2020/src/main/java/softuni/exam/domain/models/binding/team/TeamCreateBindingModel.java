package softuni.exam.domain.models.binding.team;

import org.hibernate.validator.constraints.Length;
import softuni.exam.domain.models.binding.picture.PictureCreateBindingModel;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamCreateBindingModel {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "picture")
    private PictureCreateBindingModel picture;
    private Integer id;

    public TeamCreateBindingModel() {
    }

    @NotNull
    @Length(min = 3, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public PictureCreateBindingModel getPicture() {
        return picture;
    }

    public void setPicture(PictureCreateBindingModel picture) {
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
