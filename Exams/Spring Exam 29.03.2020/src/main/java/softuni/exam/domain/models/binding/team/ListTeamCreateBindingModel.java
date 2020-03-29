package softuni.exam.domain.models.binding.team;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListTeamCreateBindingModel {

    @XmlElement(name = "team")
    private List<TeamCreateBindingModel> teams;

    public ListTeamCreateBindingModel() {
    }

    public List<TeamCreateBindingModel> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamCreateBindingModel> teams) {
        this.teams = teams;
    }
}
