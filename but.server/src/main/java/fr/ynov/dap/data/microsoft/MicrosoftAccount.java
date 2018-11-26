package fr.ynov.dap.data.microsoft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.microsoft.authentication.TokenResponse;

/**
 * Model for MicrosoftAccount.
 * @author thibault
 *
 */
@Entity
public class MicrosoftAccount {

    /**
     * Size max of token (access token & refresh token).
     */
    private static final int MAX_SIZE_TOKEN = 2048;

    /**
     * Id of entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Microsoft Account Name.
     */
    private String accountName;

    /**
     * Owner of microsoft account.
     */
    @ManyToOne
    private AppUser owner;

    /**
     * Access token.
     */
    @Column(length = MAX_SIZE_TOKEN)
    private String accessToken;

    /**
     * Expiration of access token.
     */
    private Long expirationTimeMilliseconds;

    /**
     * Refresh token.
     */
    @Column(length = MAX_SIZE_TOKEN)
    private String refreshToken;

    /**
     * Account email.
     */
    private String email;

    /**
     * TenantId of microsoft.
     */
    private String tenantId;

    /**
     * Default constructor of MicrosoftAccount.
     */
    public MicrosoftAccount() {
    }

    /**
     * Constructor of MicrosoftAccount with credential.
     * @param user Owner of this account
     * @param name account name
     * @param credential microsoft credential
     * @param tenant microsoft tenant id
     */
    public MicrosoftAccount(final String name, final AppUser user, final TokenResponse credential,
            final String tenant) {
        this.accountName = name;
        this.owner = user;
        this.tenantId = tenant;
        this.apply(credential);
    }

    /**
     * Apply a credential to this entity.
     * @param credential microsoft credential
     */
    public void apply(final TokenResponse credential) {
        this.accessToken = credential.getAccessToken();
        this.expirationTimeMilliseconds = credential.getExpirationTime().getTime();
        this.refreshToken = credential.getRefreshToken();
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param name the accountName to set
     */
    public void setAccountName(final String name) {
        this.accountName = name;
    }

    /**
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param ownerUser the owner to set
     */
    public void setOwner(final AppUser ownerUser) {
        this.owner = ownerUser;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param token the accessToken to set
     */
    public void setAccessToken(final String token) {
        this.accessToken = token;
    }

    /**
     * @return the expirationTimeMilliseconds
     */
    public Long getExpirationTimeMilliseconds() {
        return expirationTimeMilliseconds;
    }

    /**
     * @param expirationTimeMs the expirationTimeMilliseconds to set
     */
    public void setExpirationTimeMilliseconds(final Long expirationTimeMs) {
        this.expirationTimeMilliseconds = expirationTimeMs;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param token the refreshToken to set
     */
    public void setRefreshToken(final String token) {
        this.refreshToken = token;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param mEmail the email to set
     */
    public void setEmail(final String mEmail) {
        this.email = mEmail;
    }

    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenant the tenantId to set
     */
    public void setTenantId(final String tenant) {
        this.tenantId = tenant;
    }
}
