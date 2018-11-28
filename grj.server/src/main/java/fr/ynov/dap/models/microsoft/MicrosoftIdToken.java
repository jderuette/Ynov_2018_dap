package fr.ynov.dap.models.microsoft;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * MicrosoftIdToken
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftIdToken {

    /**
     * expirationTime
     */
    @JsonProperty("exp")
    private long expirationTime;

    /**
     * notBefore
     */
    @JsonProperty("nbf")
    private long notBefore;

    /**
     * tenantId
     */
    @JsonProperty("tid")
    private String tenantId;

    /**
     * nonce
     */
    private String nonce;

    /**
     * name
     */
    private String name;

    /**
     * email
     */
    private String email;

    /**
     * preferredUsername
     */
    @JsonProperty("preferred_username")
    private String preferredUsername;

    /**
     * objectId
     */
    @JsonProperty("oid")
    private String objectId;

    /**
     * Parse encoded token
     *
     * @param encodedToken encodedToken
     * @param nonce        nonce
     * @return MicrosoftIdToken
     */
    public static MicrosoftIdToken parseEncodedToken(String encodedToken, String nonce) {
        // Encoded token is in three parts, separated by '.'
        String[] tokenParts = encodedToken.split("\\.");

        // The three parts are: header.token.signature
        String idToken = tokenParts[1];

        byte[] decodedBytes = Base64.getUrlDecoder().decode(idToken);

        ObjectMapper     mapper   = new ObjectMapper();
        MicrosoftIdToken newToken = null;
        try {
            newToken = mapper.readValue(decodedBytes, MicrosoftIdToken.class);
            if (!newToken.isValid(nonce)) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newToken;
    }

    /*
    GETTERS AND SETTERS
     */

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public long getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(long notBefore) {
        this.notBefore = notBefore;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferredUsername() {
        return preferredUsername;
    }

    public void setPreferredUsername(String preferredUsername) {
        this.preferredUsername = preferredUsername;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    private Date getUnixEpochAsDate(long epoch) {
        // Epoch timestamps are in seconds,
        // but Jackson converts integers as milliseconds.
        // Rather than create a custom deserializer, this helper will do
        // the conversion.
        return new Date(epoch * 1000);
    }

    /**
     * Is Valid
     *
     * @param nonce nonce
     * @return boolean
     */
    private boolean isValid(String nonce) {
        // This method does some basic validation
        // For more information on validation of ID tokens, see
        // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#validating-tokens
        Date now = new Date();

        // Check expiration and not before times
        if (now.after(this.getUnixEpochAsDate(this.expirationTime)) ||
                now.before(this.getUnixEpochAsDate(this.notBefore))) {
            // Token is not within it's valid "time"
            return false;
        }

        // Check nonce
        return nonce.equals(this.getNonce());
    }
}
