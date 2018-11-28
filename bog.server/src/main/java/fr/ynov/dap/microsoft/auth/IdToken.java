package fr.ynov.dap.microsoft.auth;

import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Mon_PC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdToken {
    // NOTE: This is just a subset of the claims returned in the
    // ID token. For a full listing, see:
    // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#idtokens
    /**.
     * propriété expirationTime
     */
    @JsonProperty("exp")
    private long expirationTime;
    /**.
     * propriété notbefore
     */
    @JsonProperty("nbf")
    private long notBefore;
    /**.
     * propriété tenantid
     */
    @JsonProperty("tid")
    private String tenantId;
    /**.
     * propriéténonce
     */
    private String nonce;
    /**.
     * propriété name
     */
    private String name;
    /**.
     * propriété email
     */
    @JsonProperty("email")
    private String email;
    /**.
     * propriété preferredUsername
     */
    @JsonProperty("preferred_username")
    private String preferredUsername;
    /**.
     * propriété old
     */
    @JsonProperty("oid")
    private String objectId;

    /**.
     * propriété milliseconds
     */
    private static final int MILLISECONDS = 1000;

    /**
     * @param encodedToken is the encodedToken
     * @param nonce chaine
     * @return the id token
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
     * @return expirationTime
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * @param newExpirationTime set new expirationTime
     */
    public void setExpirationTime(final long newExpirationTime) {
        this.expirationTime = newExpirationTime;
    }

    /**
     * @return notBefore
     */
    public long getNotBefore() {
        return notBefore;
    }

    /**
     * @param newNotBefore set newt notBefore
     */
    public void setNotBefore(final long newNotBefore) {
        this.notBefore = newNotBefore;
    }

    /**
     * @return tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param newTenantId set new tenantId
     */
    public void setTenantId(final String newTenantId) {
        this.tenantId = newTenantId;
    }

    /**
     * @return nonce
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * @param newNonce set new nonce
     */
    public void setNonce(final String newNonce) {
        this.nonce = newNonce;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param newName set new name
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param newEmail set new email
     */
    public void setEmail(final String newEmail) {
        this.email = newEmail;
    }

    /**
     * @return preferredUsername
     */
    public String getPreferredUsername() {
        return preferredUsername;
    }

    /**
     * @param newPreferredUsername set new preferredUsername
     */
    public void setPreferredUsername(final String newPreferredUsername) {
        this.preferredUsername = newPreferredUsername;
    }

    /**
     * @return objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param newObjectId set new objectId
     */
    public void setObjectId(final String newObjectId) {
        this.objectId = newObjectId;
    }

    /**
     * @param epoch timestamp in seconds
     * @return unixEpoch
     */
    private Date getUnixEpochAsDate(final long epoch) {
        // Epoch timestamps are in seconds,
        // but Jackson converts integers as milliseconds.
        // Rather than create a custom deserializer, this helper will do
        // the conversion.
        return new Date(epoch * MILLISECONDS);
    }

    /**
     * @param newNonce id tokens
     * @return validation of id token
     */
    private boolean isValid(final String newNonce) {
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
        if (!newNonce.equals(this.getNonce())) {
            // Nonce mismatch
            return false;
        }

        return true;
    }
}
