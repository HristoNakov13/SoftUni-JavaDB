package footballbetting;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BetGameId implements Serializable {
    private Game game;
    private Bet bet;

    public BetGameId() {
    }

    public BetGameId(Game game, Bet bet) {
        this.game = game;
        this.bet = bet;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getGame(), this.getBet());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BetGameId)) return false;
        BetGameId that = (BetGameId) obj;

        return Objects.equals(this.getGame(), that.getGame()) &&
                Objects.equals(this.getBet(), that.getBet());
    }
}
