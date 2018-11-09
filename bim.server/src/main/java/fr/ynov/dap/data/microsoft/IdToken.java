package fr.ynov.dap.data.microsoft;

import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * access token.
 * @author MBILLEMAZ
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdToken {
    // NOTE: This is just a subset of the claims returned in the
    // ID token. For a full listing, see:
    // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#idtokens
    /**
     * expiration time.
     */
    @JsonProperty("exp")
    private long expirationTime;
    /**
     * not before.
     */
    @JsonProperty("nbf")
    private long notBefore;
    /**
     * tenant id.
     */
    @JsonProperty("tid")
    private String tenantId;
    /**
     * nonce.
     */
    private String nonce;
    /**
     * name.
     */
    private String name;
    /**
     * email.
     */
    private String email;
    /**
     * username.
     */
    @JsonProperty("preferred_username")
    private String preferredUsername;
    /**
     * object id.
     */
    @JsonProperty("oid")
    private String objectId;

    /**
     * token parser.
     * @param encodedToken encoded token
     * @param nonce nonce
     * @return IdToken object
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
     * getter.
     * @return value
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * update value.
     * @param expirationTime
     */
    public void setExpirationTime(final long expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * getter.
     * @return value
     */
    public long getNotBefore() {
        return notBefore;
    }

    /**
     * update value.
     * @param notBefore
     */
    public void setNotBefore(final long notBefore) {
        this.notBefore = notBefore;
    }

    /**
     * getter.
     * @return value
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * update value.
     * @param tenantId
     */
    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * getter.
     * @return value
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * update value.
     * @param nonce
     */
    public void setNonce(final String nonce) {
        this.nonce = nonce;
    }

    /**
     * getter.
     * @return value
     */
    public String getName() {
        return name;
    }

    /**
     * update value.
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * getter.
     * @return value
     */
    public String getEmail() {
        return email;
    }

    /**
     * update value.
     * @param email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * getter.
     * @return value
     */
    public String getPreferredUsername() {
        return preferredUsername;
    }

    /**
     * update value.
     * @param preferredUsername
     */
    public void setPreferredUsername(final String preferredUsername) {
        this.preferredUsername = preferredUsername;
    }

    /**
     * getter.
     * @return value
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * upadte value.
     * @param objectId
     */
    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }

    /**
     * get date by timestamp.
     * @param epoch timestamp
     * @return date
     */
    private Date getUnixEpochAsDate(final long epoch) {
        // Epoch timestamps are in seconds,
        // but Jackson converts integers as milliseconds.
        // Rather than create a custom deserializer, this helper will do
        // the conversion.
        return new Date(epoch * 1000);
    }

    /**
     * check if token is valid.
     * @param nonce nonce
     * @return if token is valid
     */
    private boolean isValid(final String nonce) {
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
        if (!nonce.equals(this.getNonce())) {
            // Nonce mismatch
            return false;
        }

        return true;
    }
}
