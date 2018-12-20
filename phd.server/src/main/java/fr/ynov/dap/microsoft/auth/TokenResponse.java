package fr.ynov.dap.microsoft.auth;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Dom.
 *
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    /**
     *
     */
    //TODO phd by Djer |JPA| Es-tu sure que descolonens "String" de cette taill là rentre dans une table d'une BDD MySQL ?
    private static final int MAX_LENGTH = 1000000;
    /**
     *
     */
    @Id
    @GeneratedValue
    //TODO phd by Djer |JPA| Name et nullable, ont déja ces valeur via l'annotation @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    /**
     *
     */
    @JsonProperty("token_type")
    private String tokenType;
    /**
     *
     */
    private String scope;
    /**
     *
     */
    @JsonProperty("expires_in")
    private int expiresIn;
    /**
     *
     */
    @Column(length = MAX_LENGTH)
    @JsonProperty("access_token")
    private String accessToken;
    /**
     *
     */
    @Column(length = MAX_LENGTH)
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**
     *
     */
    @Column(length = MAX_LENGTH)
    @JsonProperty("id_token")
    private String idToken;
    /**
     *
     */
    private String error;
    /**
     *
     */
    @JsonProperty("error_description")
    private String errorDescription;
    /**
     *
     */
    @JsonProperty("error_codes")
    private int[] errorCodes;
    /**
     *
     */
    private Date expirationTime;

    /**
     *
     * @return .
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     *
     * @param mTokenType .
     */
    public void setTokenType(final String mTokenType) {
        this.tokenType = mTokenType;
    }

    /**
     *
     * @return .
     */

    public String getScope() {
        return scope;
    }

    /**
     *
     * @param mScope .
     */
    public void setScope(final String mScope) {
        this.scope = mScope;
    }

    /**
     *
     * @return .
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     *
     * @param mExpiresIn .
     */
    public void setExpiresIn(final int mExpiresIn) {
        this.expiresIn = mExpiresIn;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, expiresIn);
        this.expirationTime = now.getTime();
    }

    /**
     *
     * @return .
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param mAccessToken .
     */

    public void setAccessToken(final String mAccessToken) {
        this.accessToken = mAccessToken;
    }

    /**
     *
     * @return .
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     *
     * @param mRefreshToken .
     */
    public void setRefreshToken(final String mRefreshToken) {
        this.refreshToken = mRefreshToken;
    }

    /**
     *
     * @return .
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     *
     * @param mIdToken .
     */
    public void setIdToken(final String mIdToken) {
        this.idToken = mIdToken;
    }

    /**
     *
     * @return .
     */
    public String getError() {
        return error;
    }

    /**
     *
     * @param mError .
     */
    public void setError(final String mError) {
        this.error = mError;
    }

    /**
     *
     * @return .
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     *
     * @param mErrorDescription .
     */
    public void setErrorDescription(final String mErrorDescription) {
        this.errorDescription = mErrorDescription;
    }

    /**
     *
     * @return .
     */
    public int[] getErrorCodes() {
        return errorCodes;
    }

    /**
     *
     * @param mErrorCodes .
     */
    public void setErrorCodes(final int[] mErrorCodes) {
        this.errorCodes = mErrorCodes;
    }

    /**
     *
     * @return .
     */
    public Date getExpirationTime() {
        return expirationTime;
    }
}
