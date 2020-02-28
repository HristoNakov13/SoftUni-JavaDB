package footballbetting;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rounds")
public class Round extends BaseEntity {
    private String name;
    private Set<Game> games;

    public Round() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "round", fetch = FetchType.LAZY)
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
