package fr.ynov.dap.model;

/**
 * @author Mon_PC
 */
public class CredentialModel {

    /**.
     * propriété accountName
     */
    private String accountName;
    /**.
     * propriété userEmail
     */
    private String userEmail;
    /**.
     * propriété tenantId
     */
    private String tenantId;
    /**.
     * propriété ownerName
     */
    private String ownerName;
    /**.
     * propriété token
     */
    private String token;

    /**.
     * propriété type
     */
    private String type;

    /**
     * @return accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param newAccountName nouveau accountName
     */
    public void setAccountName(final String newAccountName) {
        this.accountName = newAccountName;
    }

    /**
     * @return userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param newUserEmail nouveau userEmail
     */
    public void setUserEmail(final String newUserEmail) {
        this.userEmail = newUserEmail;
    }

    /**
     * @return tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param newTenantId nouveau tenantId
     */
    public void setTenantId(final String newTenantId) {
        this.tenantId = newTenantId;
    }

    /**
     * @return ownerName
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @param newOwnerName nouveau ownerName
     */
    public void setOwnerName(final String newOwnerName) {
        this.ownerName = newOwnerName;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param newToken nouveau token
     */
    public void setToken(final String newToken) {
        this.token = newToken;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param newType nouveau type
     */
    public void setType(final String newType) {
        this.type = newType;
    }
}
