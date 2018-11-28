package fr.ynov.dap.model;

/**
 * This class represent the result of the action 'AddAccount'.
 * @author Kévin Sibué
 *
 */
public class AddAccountResult {

    /**
     * Store redirect url.
     */
    private String redirectUrl = null;

    /**
     * Store success of action.
     */
    private Boolean isSuccess = false;

    /**
     * Store error description.
     */
    private String errorDescription = null;

    /**
     * @return the redirectUrl
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * @param val the redirectUrl to set
     */
    public void setRedirectUrl(final String val) {
        this.redirectUrl = val;
    }

    /**
     * @return the isSuccess
     */
    public Boolean getIsSuccess() {
        return isSuccess;
    }

    /**
     * @param val the isSuccess to set
     */
    public void setIsSuccess(final Boolean val) {
        this.isSuccess = val;
    }

    /**
     * @return the errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param val the errorDescription to set
     */
    public void setErrorDescription(final String val) {
        this.errorDescription = val;
    }

}
