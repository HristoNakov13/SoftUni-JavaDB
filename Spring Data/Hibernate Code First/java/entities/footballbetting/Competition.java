package footballbetting;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "competitions")
public class Competition extends BaseEntity {
    private String name;
    private CompetitionType competitionType;
    private Set<Game> games;

    public Competition() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition", referencedColumnName = "id")
    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competition")
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
