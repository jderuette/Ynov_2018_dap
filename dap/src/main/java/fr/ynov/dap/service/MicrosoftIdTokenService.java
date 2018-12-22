package fr.ynov.dap.service;

import java.util.Base64;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Défini les paramètres des token microsoft
 * @author abaracas
 *
 */
//TODO baa by Djer |SOA| Ca n'est pas vraiment un service (plutot un DTO avec quelques méthodes utilitaires)
public class MicrosoftIdTokenService {
	@JsonProperty("exp")
	private long expirationTime;
	@JsonProperty("nbf")
	private long notBefore;
	@JsonProperty("tid")
	private String tenantId;
	private String nonce;
	private String name;
	private String email;
	@JsonProperty("preferred_username")
	private String preferredUsername;
	@JsonProperty("oid")
	private String objectId;
	private static Logger LOG = LogManager.getLogger();
	
	/**
	 * Renvoie le nouveau token.
	 * @param encodedToken token
	 * @param nonce aucun
	 * @return le token.
	 */
	public static MicrosoftIdTokenService parseEncodedToken(String encodedToken, String nonce) {
		// Encoded token is in three parts, separated by '.'
		String[] tokenParts = encodedToken.split("\\.");
		
		// The three parts are: header.token.signature
		String idToken = tokenParts[1];
		
		byte[] decodedBytes = Base64.getUrlDecoder().decode(idToken);
		
		ObjectMapper mapper = new ObjectMapper();
		MicrosoftIdTokenService newToken = null;
		try {
			newToken = mapper.readValue(decodedBytes, MicrosoftIdTokenService.class);
			if (!newToken.isValid(nonce)) {
			    //TODO baa by Djer |Log4J| Une petite log ?
				return null;
			}
		} catch (Exception e) {
		    //TODO baa by Djer |Log4J| "e.printStackTrace()" affiche directement dans la console, utilise plutot le deuxième argument des "log" pour afficher corectement une exception
			e.printStackTrace();
			//TODO baa by Djer |log4J| Utilise le deuxième paramètre pour l'exception
			LOG.error("erreur lors de la génération du token dans microsoftdtokenservice : " + e);
		} 
		LOG.info("Token valide");
		return newToken;
	}
	
	
	private boolean isValid(String nonce) {
		// This method does some basic validation
		// For more information on validation of ID tokens, see
		// https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#validating-tokens
		Date now = new Date();
		
		// Check expiration and not before times
		if (now.after(this.getUnixEpochAsDate(this.expirationTime)) ||
				now.before(this.getUnixEpochAsDate(this.notBefore))) {
			// Token is not within it's valid "time"
		    LOG.info("Token expiré");
		    //TODO baa by Djer |POO| Evite les multiples return dans une même méthode
			return false;
		}
		
		// Check nonce
		if (!nonce.equals(this.getNonce())) {
			// Nonce mismatch
		    LOG.info("Token expiré");
		  //TODO baa by Djer |POO| Evite les multiples return dans une même méthode
			return false;
		}
		LOG.info("Token encore valide, le retourner");
		return true;
	}
	/**
	 * 
	 * @param epoch duration
	 * @return date with good format
	 */
	private Date getUnixEpochAsDate(long epoch) {
		// Epoch timestamps are in seconds,
		// but Jackson converts integers as milliseconds.
		// Rather than create a custom deserializer, this helper will do 
		// the conversion.
		return new Date(epoch * 1000);
	}


	/**
	 * @return the expirationTime
	 */
	public long getExpirationTime() {
	    return expirationTime;
	}


	/**
	 * @param expirationTime the expirationTime to set
	 */
	public void setExpirationTime(long expirationTime) {
	    this.expirationTime = expirationTime;
	}


	/**
	 * @return the notBefore
	 */
	public long getNotBefore() {
	    return notBefore;
	}


	/**
	 * @param notBefore the notBefore to set
	 */
	public void setNotBefore(long notBefore) {
	    this.notBefore = notBefore;
	}


	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
	    return tenantId;
	}


	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
	    this.tenantId = tenantId;
	}


	/**
	 * @return the nonce
	 */
	public String getNonce() {
	    return nonce;
	}


	/**
	 * @param nonce the nonce to set
	 */
	public void setNonce(String nonce) {
	    this.nonce = nonce;
	}


	/**
	 * @return the name
	 */
	public String getName() {
	    return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
	    this.name = name;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
	    return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
	    this.email = email;
	}


	/**
	 * @return the preferredUsername
	 */
	public String getPreferredUsername() {
	    return preferredUsername;
	}


	/**
	 * @param preferredUsername the preferredUsername to set
	 */
	public void setPreferredUsername(String preferredUsername) {
	    this.preferredUsername = preferredUsername;
	}


	/**
	 * @return the objectId
	 */
	public String getObjectId() {
	    return objectId;
	}


	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(String objectId) {
	    this.objectId = objectId;
	}
	
}
