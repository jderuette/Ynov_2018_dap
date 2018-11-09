package fr.ynov.dap.microsoft;

import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Dom
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdToken {
    /**
     *
     */
    private static final int CONST_DATE_MULTIPLICATOR = 1000;
    // NOTE: This is just a subset of the claims returned in the
    // ID token. For a full listing, see:
    // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#idtokens
    /**
     *
     */
    @JsonProperty("exp")
    private long expirationTime;
    /**
     *
     */
    @JsonProperty("nbf")
    private long notBefore;
    /**
     *
     */
    @JsonProperty("tid")
    private String tenantId;
    /**
     *
     */
    private String nonce;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String email;
    /**
     *
     */
    @JsonProperty("preferred_username")
    private String preferredUsername;
    /**
     *
     */
    @JsonProperty("oid")
    private String objectId;

    /**
     *
     * @param encodedToken .
     * @param nonce .
     * @return .
     */
    public static IdToken parseEncodedToken(final String encodedToken, final String nonce) {
        // Encoded token is in three parts, separated by '.'
        String[] tokenParts = encodedToken.split("\\.");

        // The three parts are: header.token.signature
        String idToken = tokenParts[1];

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
     *
     * @return .
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     *
     * @param mExpirationTime .
     */

    public void setExpirationTime(final long mExpirationTime) {
        this.expirationTime = mExpirationTime;
    }

    /**
     *
     * @return .
     */
    public long getNotBefore() {
        return notBefore;
    }

    /**
     *
     * @param mNotBefore .
     */
    public void setNotBefore(final long mNotBefore) {
        this.notBefore = mNotBefore;
    }

    /**
     *
     * @return .
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     *
     * @param mTenantId .
     */

    public void setTenantId(final String mTenantId) {
        this.tenantId = mTenantId;
    }

    /**
     *
     * @return .
     */
    public String getNonce() {
        return nonce;
    }

    /**
     *
     * @param mNonce .
     */
    public void setNonce(final String mNonce) {
        this.nonce = mNonce;
    }

    /**
     *
     * @return .
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param mName .
     */
    public void setName(final String mName) {
        this.name = mName;
    }

    /**
     *
     * @return .
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param mEmail .
     */
    public void setEmail(final String mEmail) {
        this.email = mEmail;
    }

    /**
     *
     * @return .
     */
    public String getPreferredUsername() {
        return preferredUsername;
    }

    /**
     *
     * @param mPreferredUsername .
     */

    public void setPreferredUsername(final String mPreferredUsername) {
        this.preferredUsername = mPreferredUsername;
    }

    /**
     *
     * @return .
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     *
     * @param mObjectId .
     */

    public void setObjectId(final String mObjectId) {
        this.objectId = mObjectId;
    }

    /**
     *
     * @param epoch .
     * @return .
     */

    private Date getUnixEpochAsDate(final long epoch) {
        // Epoch timestamps are in seconds,
        // but Jackson converts integers as milliseconds.
        // Rather than create a custom deserializer, this helper will do
        // the conversion.
        return new Date(epoch * CONST_DATE_MULTIPLICATOR);
    }

    /**
     *
     * @param mNonce .
     * @return .
     */
    private boolean isValid(final String mNonce) {
        // This method does some basic validation
        // For more information on validation of ID tokens, see
        // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#validating-tokens
        Date now = new Date();

        // Check expiration and not before times
        if (now.after(this.getUnixEpochAsDate(this.expirationTime))
                || now.before(this.getUnixEpochAsDate(this.notBefore))) {
            // Token is not within it's valid "time"
            return false;
        }

        // Check nonce
        if (!mNonce.equals(this.getNonce())) {
            // Nonce mismatch
            return false;
        }

        return true;
    }
}
