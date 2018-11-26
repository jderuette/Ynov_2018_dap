package fr.ynov.dap.models;

import javax.persistence.*;

/**
 * GoogleAccount model
 */
@Entity
public class GoogleAccount {

    /**
     * GoogleAccount ID
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * GoogleAccount Name
     */
    @Column
    private String name;

    /**
     * GoogleAccount Owner
     */
    @ManyToOne
    private User owner;

    /**
     * GoogleAccount ID getter
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * GoogleAccount Name getter
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * GoogleAccount Name setter
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * GoogleAccount Owner setter
     * @param owner User
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * GoogleAccount Owner getter
     * @return User
     */
    public User getOwner() {
        return owner;
    }
}
