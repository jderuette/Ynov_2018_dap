package dap.client.model.dto;

/**
 * used to map the response from server when you try to get the authorization.
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
     * @param redirect the redirection to set
     */
    public void setRedirection(final String redirect) {
        this.redirection = redirect;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param messageForUSer the message to set
     */
    public void setMessage(final String messageForUSer) {
        this.message = messageForUSer;
    }

}
