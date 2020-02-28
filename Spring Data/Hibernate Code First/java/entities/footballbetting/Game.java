package footballbetting;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    private Team homeTeam;
    private Team awayTeam;
    private int homeGoals;
    private int awayGoals;
    private LocalDate playDateTime;
    private BigDecimal homeWinBetRate;
    private BigDecimal awayWinBetRate;
    private BigDecimal drawBetRate;
    private Round round;
    private Competition competition;
//    private Set<Bet> bets;

    public Game() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team", referencedColumnName = "id")
    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team", referencedColumnName = "id")
    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Column(name = "home_team_goals")
    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    @Column(name = "away_team_goals")
    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    @Column(name = "date_time")
    public LocalDate getPlayDateTime() {
        return playDateTime;
    }

    public void setPlayDateTime(LocalDate playDateTime) {
        this.playDateTime = playDateTime;
    }

    @Column(name = "home_team_win_bet_rate")
    public BigDecimal getHomeWinBetRate() {
        return homeWinBetRate;
    }

    public void setHomeWinBetRate(BigDecimal homeWinBetRate) {
        this.homeWinBetRate = homeWinBetRate;
    }

    @Column(name = "away_team_win_bet_rate")
    public BigDecimal getAwayWinBetRate() {
        return awayWinBetRate;
    }

    public void setAwayWinBetRate(BigDecimal awayWinBetRate) {
        this.awayWinBetRate = awayWinBetRate;
    }

    @Column(name = "draw_game_bet_rate")
    public BigDecimal getDrawBetRate() {
        return drawBetRate;
    }

    public void setDrawBetRate(BigDecimal drawBetRate) {
        this.drawBetRate = drawBetRate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id", referencedColumnName = "id")
    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "bet_games",
//            joinColumns = {@JoinColumn(name = "game_id", referencedColumnName = "bet_id")},
//            inverseJoinColumns = {@JoinColumn(name = "bet_id", referencedColumnName = "id")}
//    )
//    public Set<Bet> getBets() {
//        return bets;
//    }
//
//    public void setBets(Set<Bet> bets) {
//        this.bets = bets;
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Game)) return false;
        Game that = (Game) obj;

        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
