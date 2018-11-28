package fr.ynov.dap.dap.microsoft.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.microsoft.auth.TokenResponse;

/**
 * @author Florian
 */
@Entity
public class MicrosoftAccountData {

    /**.
     * Déclaration de l'id
     */
    @Id
    @GeneratedValue
    private int id;

    /**.
     * Déclaration de owner
     */
    @ManyToOne
    private AppUser owner;

    /**.
     * Déclaration de tenantId
     */
    private String tenantId;

    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param theTenantId the tenantId to set
     */
    public void setTenantId(final String theTenantId) {
        this.tenantId = theTenantId;
    }

    /**.
     * Déclaration du nom de compte
     */
    private String accountName;

    /**.
     * Déclaration de tokenResponse
     */
    @OneToOne(cascade = CascadeType.ALL)
    private TokenResponse tokenResponse;

    /**
     * @return the tokenResponse
     */
    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    /**
     * @param theTokenResponse the theTokenResponse to set
     */
    public void setTokenResponse(final TokenResponse theTokenResponse) {
        this.tokenResponse = theTokenResponse;
    }

    /**.
     * recuperation de owner
     * @return owner
     */
    protected AppUser getOwner() {
        return owner;
    }

    /**.
     * @param theOwner Donne une valeur a owner
     */
    public void setOwner(final AppUser theOwner) {
        this.owner = theOwner;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param theAccountName the theAccountName to set
     */
    public void setAccountName(final String theAccountName) {
        this.accountName = theAccountName;
    }
}
