package fr.ynov.dap.dap.model;

/**
 *
 * @author David_tepoche
 *
 */
public class AccountResponse {
    /**
     * the redirection for the auth.
     */
    private String redirection;

    /**
     * the message returned for the front to display it.
     */
    private String message;

    /**
     * @return the redirection
     */
    public String getRedirection() {
        return redirection;
    }

    /**
     * @param redirectionGiven the redirection to set
     */
    public void setRedirection(final String redirectionGiven) {
        this.redirection = redirectionGiven;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param messageForTheUser the message to set
     */
    public void setMessage(final String messageForTheUser) {
        this.message = messageForTheUser;
    }

}
