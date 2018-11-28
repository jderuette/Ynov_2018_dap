package fr.ynov.dap.microsoft.model;

import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ynov.dap.Constants;

/**
 * IdToken from Microsoft services.
 * @author Kévin Sibué
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdToken {

    /**
     * Store expiration time.
     */
    @JsonProperty("exp")
    private long expirationTime;

    /**
     * Store not before.
     */
    @JsonProperty("nbf")
    private long notBefore;

    /**
     * Tenant Id.
     */
    @JsonProperty("tid")
    private String tenantId;

    /**
     * Nonce.
     */
    @JsonProperty("nonce")
    private String nonce;

    /**
     * Name.
     */
    @JsonProperty("name")
    private String name;

    /**
     * email.
     */
    @JsonProperty("preferred_username")
    private String email;

    /**
     * Preferred Username.
     */
    private String preferredUsername;

    /**
     * Object Id.
     */
    @JsonProperty("oid")
    private String objectId;

    /**
     * @return the objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param val the objectId to set
     */
    public void setObjectId(final String val) {
        this.objectId = val;
    }

    /**
     * @return the preferredUsername
     */
    public String getPreferredUsername() {
        return preferredUsername;
    }

    /**
     * @param val the preferredUsername to set
     */
    public void setPreferredUsername(final String val) {
        this.preferredUsername = val;
    }

    /**
     * @return the expirationTime
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * @param val the expirationTime to set
     */
    public void setExpirationTime(final long val) {
        this.expirationTime = val;
    }

    /**
     * @return the notBefore
     */
    public long getNotBefore() {
        return notBefore;
    }

    /**
     * @param val the notBefore to set
     */
    public void setNotBefore(final long val) {
        this.notBefore = val;
    }

    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param val the tenantId to set
     */
    public void setTenantId(final String val) {
        this.tenantId = val;
    }

    /**
     * @return the nonce
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * @param val the nonce to set
     */
    public void setNonce(final String val) {
        this.nonce = val;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param val the name to set
     */
    public void setName(final String val) {
        this.name = val;
    }

    /**
     * Get UNIX Date.
     * @param epoch Epoch
     * @return Unix Date
     */
    private Date getUnixEpochAsDate(final long epoch) {
        return new Date(epoch * Constants.SECOND_TO_MILLISECOND);
    }

    /**
     * Test if token is valid.
     * @param nonc Nonce
     * @return Validity of token
     */
    private boolean isValid(final String nonc) {
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
        if (!nonc.equals(this.getNonce())) {
            // Nonce mismatch
            return false;
        }

        return true;
    }

    /**
     * Parse encoded token.
     * @param encodedToken EncodedToken
     * @param nonce Nonce
     * @return Id Token
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param val the email to set
     */
    public void setEmail(final String val) {
        this.email = val;
    }

}
