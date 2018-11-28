package fr.ynov.dap.model;

public class AddAccountResponse {

    private String redirectUrl = null;

    private Boolean isSuccess = false;

    private String errorDescription = null;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(final String val) {
        this.redirectUrl = val;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(final Boolean val) {
        this.isSuccess = val;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(final String val) {
        this.errorDescription = val;
    }

}
