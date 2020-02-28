package footballbetting;

import java.io.Serializable;
import java.util.Objects;

public class PlayerStatisticsId implements Serializable {
    private Game game;
    private Player player;

    public PlayerStatisticsId() {
    }

    public PlayerStatisticsId(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int hashCode() {
        return  Objects.hash(this.getGame(), this.getPlayer());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerStatisticsId)) return false;
        PlayerStatisticsId that = (PlayerStatisticsId) o;

        return Objects.equals(this.getGame(), that.getGame()) &&
                Objects.equals(this.getPlayer(), that.getPlayer());
    }
}
