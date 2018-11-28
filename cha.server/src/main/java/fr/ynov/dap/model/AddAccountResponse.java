package fr.ynov.dap.model;

/**
 * The Class AddAccountResponse.
 */
public class AddAccountResponse {

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
     * Gets the redirect url.
     *
     * @return the redirectUrl
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Sets the redirect url.
     *
     * @param val the redirectUrl to set
     */
    public void setRedirectUrl(final String val) {
        this.redirectUrl = val;
    }

    /**
     * Gets the checks if is success.
     *
     * @return the isSuccess
     */
    public Boolean getIsSuccess() {
        return isSuccess;
    }

    /**
     * Sets the checks if is success.
     *
     * @param val the isSuccess to set
     */
    public void setIsSuccess(final Boolean val) {
        this.isSuccess = val;
    }

    /**
     * Gets the error description.
     *
     * @return the errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Sets the error description.
     *
     * @param val the errorDescription to set
     */
    public void setErrorDescription(final String val) {
        this.errorDescription = val;
    }

}
