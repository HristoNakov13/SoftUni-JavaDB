package entities;

import java.util.ArrayList;
import java.util.List;

public class Villain {
    private int id;
    private String name;
    private String evilnessFactor;
    private List<Minion> minions;

    public Villain(int id, String name) {
        this.setId(id);
        this.setName(name);
        this.minions = new ArrayList<>();
    }

    public Villain(int id, String name, String evilnessFactor) {
        this(id, name);
        this.setEvilnessFactor(evilnessFactor);
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

    public void addMinion(Minion minion) {
        this.minions.add(minion);
    }

    public List<Minion> getMinions() {
        return this.minions;
    }
}
