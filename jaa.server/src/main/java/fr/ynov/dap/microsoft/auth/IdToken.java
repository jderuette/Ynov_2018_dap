package fr.ynov.dap.microsoft.auth;

/**
 * @author adrij
 *
 */
import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * IdToken entity used by the Microsoft API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdToken {
    /**
     * One thousand. Used to fix a checkstyle syntax issue.
     */
    private static final int ONE_THOUSAND = 1000;

    // NOTE: This is just a subset of the claims returned in the
    // ID token. For a full listing, see:
    // https://docs.microsoft.com/en-us/azure/active-directory/develop/id-tokens
    // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#idtokens
    /**
     * The expiration time identifies the expiration time on or after which the JWT must not be accepted for processing.
     */
    @JsonProperty("exp")
    private long expirationTime;
    /**
     * The "nbf" (not before) claim identifies the time before which the JWT must not be accepted for processing.
     */
    @JsonProperty("nbf")
    private long notBefore;
    /**
     * Represents the Azure AD tenant that the user is from.
     */
    @JsonProperty("tid")
    private String tenantId;
    /**
     * A unique identifier used to protect against token replay attacks.
     */
    private String nonce;
    /**
     * Provides a human-readable value that identifies the subject of the token.
     */
    private String name;
    /**
     * The email claim is present by default for guest accounts that have an email address.
     */
    private String email;
    /**
     * Only present in v2.0 tokens. The primary username that represents the user.
     */
    @JsonProperty("preferred_username")
    private String preferredUsername;
    /**
     * The immutable identifier for an object in the Microsoft identity platform, in this case, a user account.
     */
    @JsonProperty("oid")
    private String objectId;

    /**
     * Parse the encoded data to retrieve the IdToken.
     * @param encodedToken encoded token.
     * @param nonce nonce
     * @return the IdToken.
     */
    public static IdToken parseEncodedToken(final String encodedToken, final String nonce) {
        // Encoded token is in three parts, separated by '.'
        String[] tokenParts = encodedToken.split("\\.");
        String idToken = getTheTokenPart(tokenParts);

        byte[] decodedBytes = Base64.getUrlDecoder().decode(idToken);

        ObjectMapper mapper = new ObjectMapper();
        IdToken newToken = null;
        try {
            newToken = mapper.readValue(decodedBytes, IdToken.class);
            if (!newToken.isValid(nonce)) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newToken;
    }

    /**
     * Get the token part of the tokenParts.
     * @param tokenParts token parts.
     * @return the token.
     */
    private static String getTheTokenPart(final String[] tokenParts) {
     // The three parts are: header.token.signature
        String idToken = tokenParts[1];
        return idToken;
    }

    /**
     * ExpirationTime getter.
     * @return Expiration Time.
     */
    public long getExpirationTime() {
        return expirationTime;
    }
    /**
     * ExpirationTime setter.
     * @param expiration expiration time.
     */
    public void setExpirationTime(final long expiration) {
        this.expirationTime = expiration;
    }
    /**
     * NotBefore getter.
     * @return not before.
     */
    public long getNotBefore() {
        return notBefore;
    }
    /**
     * NotBefore setter.
     * @param before not before.
     */
    public void setNotBefore(final long before) {
        this.notBefore = before;
    }
    /**
     * TenantId getter.
     * @return tenant id.
     */
    public String getTenantId() {
        return tenantId;
    }
    /**
     * TenantId setter.
     * @param id tenantId.
     */
    public void setTenantId(final String id) {
        this.tenantId = id;
    }
    /**
     * Nonce getter.
     * @return Nonce.
     */
    public String getNonce() {
        return nonce;
    }
    /**
     * Nonce setter.
     * @param n nonce.
     */
    public void setNonce(final String n) {
        this.nonce = n;
    }
    /**
     * Name getter.
     * @return name.
     */
    public String getName() {
        return name;
    }
    /**
     * Name setter.
     * @param n name.
     */
    public void setName(final String n) {
        this.name = n;
    }
    /**
     * Email getter.
     * @return email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Email setter.
     * @param e email.
     */
    public void setEmail(final String e) {
        this.email = e;
    }
    /**
     * PreferredUsername getter.
     * @return preferred username (seems like an email?!).
     */
    public String getPreferredUsername() {
        return preferredUsername;
    }
    /**
     * PreferredUsername setter.
     * @param username preferred username.
     */
    public void setPreferredUsername(final String username) {
        this.preferredUsername = username;
    }
    /**
     * ObjectId getter.
     * @return objectId.
     */
    public String getObjectId() {
        return objectId;
    }
    /**
     * ObjectId setter.
     * @param object ObjectId.
     */
    public void setObjectId(final String object) {
        this.objectId = object;
    }
    /**
     * Convert UnixEpoch to Date.
     * @param epoch Unix epoch
     * @return date.
     */
    private Date getUnixEpochAsDate(final long epoch) {
        // Epoch timestamps are in seconds, but Jackson converts integers as milliseconds.
        // Rather than create a custom deserializer, this helper will do the conversion.
        return new Date(epoch * ONE_THOUSAND);
    }

    /**
     * This method does some basic validation.
     * For more information on validation of ID tokens, see
     * https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#validating-tokens
     * @param n nonce.
     * @return if the once is valid.
     */
    private boolean isValid(final String n) {
        Date now = new Date();
        Boolean isExpirationInAnIncorrectInterval = now.after(this.getUnixEpochAsDate(this.expirationTime))
                || now.before(this.getUnixEpochAsDate(this.notBefore));
        if (isExpirationInAnIncorrectInterval) {
            return false;
        }

        if (!n.equals(this.getNonce())) {
            return false;
        }

        return true;
    }
}
