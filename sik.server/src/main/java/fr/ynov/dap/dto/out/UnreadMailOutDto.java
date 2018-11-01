package fr.ynov.dap.dto.out;

/**
 * Represent number of unread mail for a user.
 * @author Kévin Sibué
 *
 */
public class UnreadMailOutDto {

    /**
     * Store number of unread mail.
     */
    private Integer numberOfUnreadMail;

    /**
     * Default constructor.
     * @param nbMail Number of unread mail
     */
    public UnreadMailOutDto(final Integer nbMail) {
        this.setNumberOfUnreadMail(nbMail);
    }

    /**
     * @return the numberOfUnreadMail
     */
    public Integer getNumberOfUnreadMail() {
        return numberOfUnreadMail;
    }

    /**
     * @param val the numberOfUnreadMail to set.
     */
    public void setNumberOfUnreadMail(final Integer val) {
        this.numberOfUnreadMail = val;
    }

}
