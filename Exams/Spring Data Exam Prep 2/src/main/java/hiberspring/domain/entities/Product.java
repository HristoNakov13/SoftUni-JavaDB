package hiberspring.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private Integer clients;
    private Branch branch;

    public Product() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "clients", nullable = false)
    public Integer getClients() {
        return clients;
    }

    public void setClients(Integer clients) {
        this.clients = clients;
    }

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id", nullable = false)
    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
