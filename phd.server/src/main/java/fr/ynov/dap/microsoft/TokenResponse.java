package fr.ynov.dap.microsoft;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Dom.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
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
    @JsonProperty("access_token")
    private String accessToken;
    /**
     *
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**
     *
     */
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
