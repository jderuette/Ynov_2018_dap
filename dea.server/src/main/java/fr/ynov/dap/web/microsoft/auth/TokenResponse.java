
package fr.ynov.dap.web.microsoft.auth;


import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Classe TokenResponse
 * 
 * @author antod
 *
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse
{
  /**
   * Id du tokenResponse
   */
  @Id
  @GeneratedValue
  private Integer Id;

  /**
   * Variable tokenType
   */
  @JsonProperty("token_type")
  private String tokenType;
  /**
   * Variable scope
   */
  private String scope;
  /**
   * Variable expiresIn
   */
  @JsonProperty("expires_in")
  private int expiresIn;
  /**
   * Variable accessToken
   */
  @JsonProperty("access_token")
  //TODO dea by Djer |JPA| lorsque tu essaye de sauvegarder un "micrsofot account", (et le"response Token" associé" tu as uen exception "Data too long for column 'access_token' at row 1". Par defaut les "String" en JPA ont une "petite taille". Tu peux le modifier avec un "@Column(length=xxxxx)"
  private String accessToken;
  /**
   * Variable refreshToken
   */
  @JsonProperty("refresh_token")
  //TODO dea by Djer |JPA| Ici aussi adapte la taille de la colonne
  private String refreshToken;
  /**
   * Variable idToken
   */
  @JsonProperty("id_token")
  private String idToken;
  /**
   * Variable error
   */
  private String error;
  /**
   * Variable errorDescription
   */
  @JsonProperty("error_description")
  private String errorDescription;
  /**
   * Variable errorCodes
   */
  @JsonProperty("error_codes")
  private int[] errorCodes;
  /**
   * Variable expirationTime
   */
  private Date expirationTime;

  /**
   * Récupère le tokenType
   * 
   * @return
   */
  public String getTokenType()
  {
    return tokenType;
  }

  /**
   * Assigne le tokenType
   * 
   * @param tokenType
   */
  public void setTokenType(String tokenType)
  {
    this.tokenType = tokenType;
  }

  /**
   * Récupère le scope
   * 
   * @return
   */
  public String getScope()
  {
    return scope;
  }

  /**
   * Assigne le scope
   * 
   * @param scope
   */
  public void setScope(String scope)
  {
    this.scope = scope;
  }

  /**
   * Récupère expiresIn
   * 
   * @return
   */
  public int getExpiresIn()
  {
    return expiresIn;
  }

  /**
   * Assigne expiresIn
   * 
   * @param expiresIn
   */
  public void setExpiresIn(int expiresIn)
  {
    this.expiresIn = expiresIn;
    Calendar now = Calendar.getInstance();
    now.add(Calendar.SECOND, expiresIn);
    this.expirationTime = now.getTime();
  }

  /**
   * Récupère l'accessToken
   * 
   * @return
   */
  public String getAccessToken()
  {
    return accessToken;
  }

  /**
   * Assigne l'accessToken
   * 
   * @param accessToken
   */
  public void setAccessToken(String accessToken)
  {
    this.accessToken = accessToken;
  }

  /**
   * Récupère l'accessToken
   * 
   * @return
   */
  public String getRefreshToken()
  {
    return refreshToken;
  }

  /**
   * Assigne le refreshToken
   * 
   * @param refreshToken
   */
  public void setRefreshToken(String refreshToken)
  {
    this.refreshToken = refreshToken;
  }

  /**
   * Récupère l'idToken
   * 
   * @return
   */
  public String getIdToken()
  {
    return idToken;
  }

  /**
   * Assigne l'idToken
   * 
   * @param idToken
   */
  public void setIdToken(String idToken)
  {
    this.idToken = idToken;
  }

  /**
   * Récupère error
   * 
   * @return
   */
  public String getError()
  {
    return error;
  }

  /**
   * Assigne error
   * 
   * @param error
   */
  public void setError(String error)
  {
    this.error = error;
  }

  /**
   * Récupère errorDescription
   * 
   * @return
   */
  public String getErrorDescription()
  {
    return errorDescription;
  }

  /**
   * Assigne errorDescription
   * 
   * @param errorDescription
   */
  public void setErrorDescription(String errorDescription)
  {
    this.errorDescription = errorDescription;
  }

  /**
   * Récupère les codes d'erreur
   * 
   * @return
   */
  public int[] getErrorCodes()
  {
    return errorCodes;
  }

  /**
   * Assigne les codes d'erreur
   * 
   * @param errorCodes
   */
  public void setErrorCodes(int[] errorCodes)
  {
    this.errorCodes = errorCodes;
  }

  /**
   * Récupère expirationTime
   * 
   * @return
   */
  public Date getExpirationTime()
  {
    return expirationTime;
  }

  /**
   * @return the id
   */
  public Integer getId()
  {
    return Id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Integer id)
  {
    Id = id;
  }

  /**
   * @param expirationTime the expirationTime to set
   */
  public void setExpirationTime(Date expirationTime)
  {
    this.expirationTime = expirationTime;
  }

}
