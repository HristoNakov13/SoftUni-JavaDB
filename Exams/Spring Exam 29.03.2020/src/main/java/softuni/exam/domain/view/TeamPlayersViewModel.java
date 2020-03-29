package softuni.exam.domain.view;

import java.util.List;

public class TeamPlayersViewModel {

    private String name;
    private List<PlayerPlayingForViewModel> players;

    public TeamPlayersViewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerPlayingForViewModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerPlayingForViewModel> players) {
        this.players = players;
    }
}
