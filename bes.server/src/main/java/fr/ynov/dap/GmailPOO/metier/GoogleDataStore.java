/**
 * 
 */
package fr.ynov.dap.GmailPOO.metier;

import com.google.api.client.auth.oauth2.Credential;

/**
 * @author acer
 *
 */
//TODO bes by Djer |POO| une petite Javadoc serait utile. Ce code ne semble pas (plus) utilisé. A supprimer ? 
public class GoogleDataStore {
private String accountName;
private Credential credential;
private String accessToken;
private String refreshToken;
private Long expirationTimeMilliseconds;
/**
 * 
 */
public GoogleDataStore() {
	super();
	// TODO Auto-generated constructor stub
}
/**
 * @param accountName
 * @param credential
 */
public GoogleDataStore(String accountName, Credential credential) {
	super();
	this.accountName = accountName;
	this.credential = credential;
	this.accessToken=credential.getAccessToken();
	this.refreshToken=credential.getRefreshToken();
	this.expirationTimeMilliseconds=credential.getExpirationTimeMilliseconds();
}
/**
 * @return the accountName
 */
public String getAccountName() {
	return accountName;
}
/**
 * @param accountName the accountName to set
 */
public void setAccountName(String accountName) {
	this.accountName = accountName;
}
/**
 * @return the credential
 */
public Credential getCredential() {
	return credential;
}
/**
 * @param credential the credential to set
 */
public void setCredential(Credential credential) {
	this.credential = credential;
}
/**
 * @return the accessToken
 */
public String getAccessToken() {
	return accessToken;
}
/**
 * @param accessToken the accessToken to set
 */
public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
}
/**
 * @return the refreshToken
 */
public String getRefreshToken() {
	return refreshToken;
}
/**
 * @param refreshToken the refreshToken to set
 */
public void setRefreshToken(String refreshToken) {
	this.refreshToken = refreshToken;
}
/**
 * @return the expirationTimeMilliseconds
 */
public Long getExpirationTimeMilliseconds() {
	return expirationTimeMilliseconds;
}
/**
 * @param expirationTimeMilliseconds the expirationTimeMilliseconds to set
 */
public void setExpirationTimeMilliseconds(Long expirationTimeMilliseconds) {
	this.expirationTimeMilliseconds = expirationTimeMilliseconds;
}

	
	
	
}
