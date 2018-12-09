package fr.ynov.dap.microsoft.authentication;

import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Data IdToken.
 * @author thibault
 *
 */
//TODO but by Djer |POO| Je sait que c'est proposé dnas le tuto mais mélangé un DTO avec quelques traitement "métier" n'est pas ce qu'on à fait dans les autres classe (parseEncodedToken(), isValid() pourraient être déplacées) 
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdToken {
    /**
     * Multiplicator to convert second to m-seconds.
     */
    private static final int MULTIPLICATOR_MS = 1000;

    /**
     * Expiration timestamp of token.
     */
    @JsonProperty("exp")
    private long expirationTime;

    /**
     * Start validity timestamp of token.
     */
    @JsonProperty("nbf")
    private long notBefore;

    /**
     * Tenant id of token.
     */
    @JsonProperty("tid")
    private String tenantId;

    /**
     * Nonce of token.
     */
    private String nonce;

    /**
     * Name of owner token.
     */
    private String name;

    /**
     * Email of owner token.
     */
    private String email;

    /**
     * Preferred username of owner token.
     */
    @JsonProperty("preferred_username")
    private String preferredUsername;

    /**
     * Object Id.
     */
    @JsonProperty("oid")
    private String objectId;

    /**
     * Parse token encoded.
     * @param encodedToken token encoded.
     * @param nonce nonce of token.
     * @return Token uncoded.
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
                //TODO but by Djer |log4J| Une petite log ?
                return null;
            }
        } catch (Exception e) {
            //TODO but by Djer |log4J| Une petite log ? Pas de "e.printStackTrace()" car cela ecrit directement dans la console !
            e.printStackTrace();
        }
        return newToken;
    }

    /**
     * @return the expirationTime
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * @param expirationTimeToSet the expirationTime to set
     */
    public void setExpirationTime(final long expirationTimeToSet) {
        this.expirationTime = expirationTimeToSet;
    }

    /**
     * @return the notBefore
     */
    public long getNotBefore() {
        return notBefore;
    }

    /**
     * @param notBeforeToSet the notBefore to set
     */
    public void setNotBefore(final long notBeforeToSet) {
        this.notBefore = notBeforeToSet;
    }

    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantIdToSet the tenantId to set
     */
    public void setTenantId(final String tenantIdToSet) {
        this.tenantId = tenantIdToSet;
    }

    /**
     * @return the nonce
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * @param nonceToSet the nonce to set
     */
    public void setNonce(final String nonceToSet) {
        this.nonce = nonceToSet;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param nameToSet the name to set
     */
    public void setName(final String nameToSet) {
        this.name = nameToSet;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param emailToSet the email to set
     */
    public void setEmail(final String emailToSet) {
        this.email = emailToSet;
    }

    /**
     * @return the preferredUsername
     */
    public String getPreferredUsername() {
        return preferredUsername;
    }

    /**
     * @param preferredUsernameToSet the preferredUsername to set
     */
    public void setPreferredUsername(final String preferredUsernameToSet) {
        this.preferredUsername = preferredUsernameToSet;
    }

    /**
     * @return the objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param objectIdToSet the objectId to set
     */
    public void setObjectId(final String objectIdToSet) {
        this.objectId = objectIdToSet;
    }

    /**
     * Get Unix timestamp of epoch.
     * @param epoch original epoch in second.
     * @return timestamp in ms.
     */
    private Date getUnixEpochAsDate(final long epoch) {
        return new Date(epoch * MULTIPLICATOR_MS);
    }

    /**
     * Token is valid ?
     * @param nonceToken nonce of token
     * @return true if is valid
     */
    private boolean isValid(final String nonceToken) {
        // This method does some basic validation
        // For more information on validation of ID tokens, see
        // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#validating-tokens
        Date now = new Date();

        // Check expiration and not before times
        if (now.after(this.getUnixEpochAsDate(this.expirationTime))
                || now.before(this.getUnixEpochAsDate(this.notBefore))) {
            // Token is not within it's valid "time"
           //TODO but by Djer |log4J| Une petite log ? (en warning à priori)
            return false;
        }

        // Check nonce
        if (!nonceToken.equals(this.getNonce())) {
            // Nonce mismatch
          //TODO but by Djer |log4J| Une petite log ? (en warning à priori)
            return false;
        }

        return true;
    }
}
