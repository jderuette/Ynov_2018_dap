package fr.ynov.dap.models;

import javax.persistence.*;

@Entity
public class GoogleAccount {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @ManyToOne
    private User owner;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }
}
