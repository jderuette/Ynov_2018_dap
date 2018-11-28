package fr.ynov.dap.dap.model;

import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class OutlookIdToken.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookIdToken {
  // NOTE: This is just a subset of the claims returned in the
  // ID token. For a full listing, see:
  /** The expiration time. */
  // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#idtokens
  @JsonProperty("exp")
  private long expirationTime;
  
  /** The not before. */
  @JsonProperty("nbf")
  private long notBefore;
  
  /** The tenant id. */
  @JsonProperty("tid")
  private String tenantId;
  
  /** The nonce. */
  private String nonce;
  
  /** The name. */
  private String name;
  
  /** The email. */
  private String email;
  
  /** The preferred username. */
  @JsonProperty("preferred_username")
  private String preferredUsername;
  
  /** The object id. */
  @JsonProperty("oid")
  private String objectId;

  /**
   * Parses the encoded token.
   *
   * @param encodedToken the encoded token
   * @param nonce the nonce
   * @return the outlook id token
   */
  public static OutlookIdToken parseEncodedToken(String encodedToken, String nonce) {
    // Encoded token is in three parts, separated by '.'
    String[] tokenParts = encodedToken.split("\\.");

    // The three parts are: header.token.signature
    String idToken = tokenParts[1];

    byte[] decodedBytes = Base64.getUrlDecoder().decode(idToken);

    ObjectMapper mapper = new ObjectMapper();
    OutlookIdToken newToken = null;
    try {
      newToken = mapper.readValue(decodedBytes, OutlookIdToken.class);
      if (!newToken.isValid(nonce)) {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return newToken;
  }

  /**
   * Gets the expiration time.
   *
   * @return the expiration time
   */
  public long getExpirationTime() {
    return expirationTime;
  }

  /**
   * Sets the expiration time.
   *
   * @param expirationTime the new expiration time
   */
  public void setExpirationTime(long expirationTime) {
    this.expirationTime = expirationTime;
  }

  /**
   * Gets the not before.
   *
   * @return the not before
   */
  public long getNotBefore() {
    return notBefore;
  }

  /**
   * Sets the not before.
   *
   * @param notBefore the new not before
   */
  public void setNotBefore(long notBefore) {
    this.notBefore = notBefore;
  }

  /**
   * Gets the tenant id.
   *
   * @return the tenant id
   */
  public String getTenantId() {
    return tenantId;
  }

  /**
   * Sets the tenant id.
   *
   * @param tenantId the new tenant id
   */
  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  /**
   * Gets the nonce.
   *
   * @return the nonce
   */
  public String getNonce() {
    return nonce;
  }

  /**
   * Sets the nonce.
   *
   * @param nonce the new nonce
   */
  public void setNonce(String nonce) {
    this.nonce = nonce;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email.
   *
   * @param email the new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the preferred username.
   *
   * @return the preferred username
   */
  public String getPreferredUsername() {
    return preferredUsername;
  }

  /**
   * Sets the preferred username.
   *
   * @param preferredUsername the new preferred username
   */
  public void setPreferredUsername(String preferredUsername) {
    this.preferredUsername = preferredUsername;
  }

  /**
   * Gets the object id.
   *
   * @return the object id
   */
  public String getObjectId() {
    return objectId;
  }

  /**
   * Sets the object id.
   *
   * @param objectId the new object id
   */
  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  /**
   * Gets the unix epoch as date.
   *
   * @param epoch the epoch
   * @return the unix epoch as date
   */
  private Date getUnixEpochAsDate(long epoch) {
    // Epoch timestamps are in seconds,
    // but Jackson converts integers as milliseconds.
    // Rather than create a custom deserializer, this helper will do 
    // the conversion.
    return new Date(epoch * 1000);
  }

  /**
   * Checks if is valid.
   *
   * @param nonce the nonce
   * @return true, if is valid
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
    if (!nonce.equals(this.getNonce())) {
      // Nonce mismatch
      return false;
    }

    return true;
  }
}