package com.ynov.dap.google.models;

/**
 * Model contact.
 * @author POL
 */
public class ContactModel {


    /** The nb contacts. */
    private Integer nbContacts;

    /**
     * Gets the nb contacts.
     *
     * @return the nb contacts
     */
    public Integer getNbContacts() {
        return nbContacts;
    }

    /**
     * Sets the nb contacts.
     *
     * @param inNbContacts the new nb contacts
     */
    public void setNbContacts(final Integer inNbContacts) {
       this.nbContacts = inNbContacts;
    }

    /**
     * Instantiates a new contact model.
     *
     * @param inNbContacts the in nb contacts
     */
    public ContactModel(final Integer inNbContacts) {
       this.setNbContacts(inNbContacts);
    }

}
