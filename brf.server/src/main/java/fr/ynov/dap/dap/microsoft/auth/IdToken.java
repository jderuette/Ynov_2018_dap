package fr.ynov.dap.dap.microsoft.auth;

import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Florian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdToken {
    // NOTE: This is just a subset of the claims returned in the
    // ID token. For a full listing, see:
    // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#idtokens

    /**.
     * Déclaration de la constante NOMBREDATE
     */
    private static final int NOMBREDATE = 1000;

    /**.
     * Déclaration de expirationTime
     */
    @JsonProperty("exp")
    private long expirationTime;
    /**.
     * Déclaration de notBefore
     */
    @JsonProperty("nbf")
    private long notBefore;
    /**.
     * Déclaration de tenantId
     */
    @JsonProperty("tid")
    private String tenantId;
    /**.
     * Déclaration de nonce
     */
    private String nonce;
    /**.
     * Déclaration de name
     */
    private String name;
    /**.
     * Déclaration de email
     */
    private String email;
    /**.
     * Déclaration de preferredUsername
     */
    @JsonProperty("preferred_username")
    private String preferredUsername;
    /**.
     * Déclaration de objectId
     */
    @JsonProperty("oid")
    private String objectId;

    /**
     * @param encodedToken .
     * @param nonce .
     * @return newToken
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
     * @param theExpirationTime Modification de la valeur
     */
    public void setExpirationTime(final long theExpirationTime) {
        this.expirationTime = theExpirationTime;
    }

    /**
     * @return notBefore
     */
    public long getNotBefore() {
        return notBefore;
    }

    /**
     * @param theNotBefore Modification de la valeur
     */
    public void setNotBefore(final long theNotBefore) {
        this.notBefore = theNotBefore;
    }

    /**
     * @return tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param theTenantId Modification de la valeur
     */
    public void setTenantId(final String theTenantId) {
        this.tenantId = theTenantId;
    }

    /**
     * @return nonce
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * @param theNonce Modification de la valeur
     */
    public void setNonce(final String theNonce) {
        this.nonce = theNonce;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param theName Modification de la valeur
     */
    public void setName(final String theName) {
        this.name = theName;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param theEmail Modification de la valeur
     */
    public void setEmail(final String theEmail) {
        this.email = theEmail;
    }

    /**
     * @return preferredUsername
     */
    public String getPreferredUsername() {
        return preferredUsername;
    }

    /**
     * @param thePreferredUsername Modification de la valeur
     */
    public void setPreferredUsername(final String thePreferredUsername) {
        this.preferredUsername = thePreferredUsername;
    }

    /**
     * @return objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param theObjectId Modification de la valeur
     */
    public void setObjectId(final String theObjectId) {
        this.objectId = theObjectId;
    }

    /**
     * @param epoch .
     * @return une date
     */
    private Date getUnixEpochAsDate(final long epoch) {
        // Epoch timestamps are in seconds,
        // but Jackson converts integers as milliseconds.
        // Rather than create a custom deserializer, this helper will do 
        // the conversion.
        return new Date(epoch * NOMBREDATE);
    }

    /**
     * @param theNonce .
     * @return true ou false
     */
    private boolean isValid(final String theNonce) {
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
        if (!theNonce.equals(this.getNonce())) {
            // Nonce mismatch
            return false;
        }

        return true;
    }
}
