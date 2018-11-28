package fr.ynov.dap.microsoft.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * @author Mon_PC
 *
 */
@Entity
public class MicrosoftAccountData {
    /**.
     * Attribut id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**.
     * Attribut accountName
     */
    @Column(unique = true)
    private String accountName;

    /**.
     * Attribut userTenantId
     */
    private String userTenantId;

    /**.
     * Attribut userEmail
     */
    private String userEmail;

    /**
     * List of every google account for this user.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idToken")
    private TokenResponse token;

    /**.
     * Attribut owner
     */
    @ManyToOne
    private AppUser owner;

    /**
     * @return owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param newOwner AppUser
     */
    public void setOwner(final AppUser newOwner) {
        owner = newOwner;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param value of the new accountName
     */
    public void setAccountName(final String value) {
        accountName = value;
    }

    /**
     * @return token
     */
    public TokenResponse getToken() {
        return token;
    }

    /**.
     * @param newToken correspondant au nouveau token
     */
    public void setToken(final TokenResponse newToken) {
        this.token = newToken;
    }

    /**
     * @return userTenantId
     */
    public String getUserTenantId() {
        return userTenantId;
    }

    /**.
     * @param newUserTenantId correspondant au nouveau tenantId
     */
    public void setUserTenantId(final String newUserTenantId) {
        this.userTenantId = newUserTenantId;
    }

    /**
     * @return userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**.
     * @param newUserEmail correspondant au nouveau userEmail
     */
    public void setUserEmail(final String newUserEmail) {
        this.userEmail = newUserEmail;
    }
}
