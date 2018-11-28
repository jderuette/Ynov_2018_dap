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
 *
 * @author Dom .
 *
 */
@Entity
public class MicrosoftAccountData {
    /**.
     *
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**.
     *
     */
    @Column(unique = true)
    private String accountName;

    /**
     * List of every google account for this user.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_token")
    private TokenResponse tokens;

    /**.
     *
     */
    private String userTenantId;

    /**.
     *
     */
    private String userEmail;

    /**.
     *
     */
    @ManyToOne
    private AppUser owner;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param mId the id to set
     */
    public void setId(final Integer mId) {
        this.id = mId;
    }

    /**
     * @return the tokens
     */
    public TokenResponse getTokens() {
        return tokens;
    }

    /**
     * @param mTokens the tokens to set
     */
    public void setTokens(final TokenResponse mTokens) {
        this.tokens = mTokens;
    }

    /**
     * @return the userTenantId
     */
    public String getUserTenantId() {
        return userTenantId;
    }

    /**
     * @param mUserTenantId the userTenantId to set
     */
    public void setUserTenantId(final String mUserTenantId) {
        this.userTenantId = mUserTenantId;
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param mUserEmail the userEmail to set
     */
    public void setUserEmail(final String mUserEmail) {
        this.userEmail = mUserEmail;
    }

    /**
     * @return the owner
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
}
