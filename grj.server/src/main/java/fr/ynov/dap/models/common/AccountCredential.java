package fr.ynov.dap.models.common;

/**
 * AccountCredential
 */
public class AccountCredential {

    /**
     * Account name
     */
    private String accountName;

    /**
     * Type
     */
    private String type;

    /**
     * Token
     */
    private String token;

    /**
     * Tenant id
     */
    private String tenantId;


    /*
    GETTERS AND SETTERS
     */

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
