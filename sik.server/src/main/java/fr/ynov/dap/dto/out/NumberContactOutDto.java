package fr.ynov.dap.dto.out;

/**
 * Represent number of contact for a user.
 * @author Kévin Sibué
 *
 */
public class NumberContactOutDto {

    /**
     * Store number of contacts.
     */
    private Integer numberOfContacts;

    /**
     * Default constructor.
     * @param nbContact Nb contact
     */
    public NumberContactOutDto(final Integer nbContact) {
        numberOfContacts = nbContact;
    }

    /**
     * @return the numberOfContacts
     */
    public Integer getNumberOfContacts() {
        return numberOfContacts;
    }

    /**
     * @param val the numberOfContacts to set
     */
    public void setNumberOfContacts(final Integer val) {
        this.numberOfContacts = val;
    }

}
