package footballbetting;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "colors")
public class Color extends BaseEntity {
    private String name;
    private Set<Team> teams;

    public Color() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY)
    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}
