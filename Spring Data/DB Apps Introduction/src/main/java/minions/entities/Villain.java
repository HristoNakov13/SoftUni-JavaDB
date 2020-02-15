package minions.entities;

import java.util.ArrayList;
import java.util.List;

public class Villain {
    private int id;
    private String name;
    private String evilnessFactor;
    private List<Minion> minions;

    public Villain() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvilnessFactor() {
        return evilnessFactor;
    }

    public void setEvilnessFactor(String evilnessFactor) {
        this.evilnessFactor = evilnessFactor;
    }

    public List<Minion> getMinions() {
        return this.minions;
    }

    public void setMinions(List<Minion> minions) {
        this.minions = minions;
    }
}
