package footballbetting;

import javax.persistence.*;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity {
    private String description;

    public Position() {
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}