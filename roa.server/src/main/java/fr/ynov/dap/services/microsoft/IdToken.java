package fr.ynov.dap.services.microsoft;

import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author alexa
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
   * notbefore.
   */
  @JsonProperty("nbf")
  private long notBefore;
  /**
   * tenantId.
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
   * preferred username.
   */
  @JsonProperty("preferred_username")
  private String preferredUsername;
  /**
   * objectId.
   */
  @JsonProperty("oid")
  private String objectId;

  /**
   * parseur de token encodé.
   * @param encodedToken toen
   * @param nonce nonce
   * @return String new token
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
      //TODO roa by Djer |Log4J| Une petite log ? "e.printStackTrace()" Afficeh direectement dans la console, contrairement aux log qui sont configurables
      e.printStackTrace();
    }
    return newToken;
  }

  /**
   * get expiration time.
   * @return long expiration time
   */
  public long getExpirationTime() {
    return expirationTime;
  }

  /**
   * set expiration time.
   * @param expirationtime long
   */
  public void setExpirationTime(final long expirationtime) {
    this.expirationTime = expirationtime;
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

  private boolean isValid(String nonce) {
    // This method does some basic validation
    // For more information on validation of ID tokens, see
    // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#validating-tokens
    Date now = new Date();

    // Check expiration and not before times
    if (now.after(this.getUnixEpochAsDate(this.expirationTime)) ||
        now.before(this.getUnixEpochAsDate(this.notBefore))) {
      //TODO roa by Djer |Log4J| Une petite log ?
        //TODO roa by Djer |POO| Evite les multiples returns dans une même méthode
      // Token is not within it's valid "time"
      return false;
    }

    // Check nonce
    if (!nonce.equals(this.getNonce())) {
      // Nonce mismatch
      //TODO roa by Djer |Log4J| Une petite log ?
      //TODO roa by Djer |POO| Evite les multiples returns dans une même méthode
      return false;
    }

    return true;
  }
}