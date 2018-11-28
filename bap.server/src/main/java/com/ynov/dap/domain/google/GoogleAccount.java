package com.ynov.dap.domain.google;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ynov.dap.domain.AppUser;

/**
 * The Class GoogleAccount.
 */
@Entity
public class GoogleAccount {

    /** The id. */
    @Id
    @GeneratedValue
    private Integer id;

    /** The owner. */
    @ManyToOne
    private AppUser owner;

    /** The name. */
    private String name;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Gets the owner.
     *
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * Sets the owner.
     *
     * @param owner the new owner
     */
    public void setOwner(final AppUser owner) {
        this.owner = owner;
    }
}