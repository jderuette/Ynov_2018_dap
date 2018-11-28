package fr.ynov.dap.microsoft;

import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ynov.dap.Constant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {

    @JsonProperty("exp")
    private long expirationTime;

    @JsonProperty("nbf")
    private long notBefore;

    @JsonProperty("tid")
    private String tenantId;

    @JsonProperty("nonce")
    private String nonce;

    @JsonProperty("name")
    private String name;

    @JsonProperty("preferred_username")
    private String email;

    @JsonProperty("preferred_username")
    private String preferredUsername;

    @JsonProperty("oid")
    private String objectId;

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(final long val) {
        this.expirationTime = val;
    }

    public long getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(final long val) {
        this.notBefore = val;
    }


    public String getTenantId() {
        return tenantId;
    }

	/**/
    public void setTenantId(final String val) {
        this.tenantId = val;
    }


    public String getNonce() {
        return nonce;
    }

    public void setNonce(final String val) {
        this.nonce = val;
    }

    public String getName() {
        return name;
    }

    public void setName(final String val) {
        this.name = val;
    }

    private Date getUnixEpochAsDate(final long epoch) {
        return new Date(epoch * Constant.SECOND_TO_MILLISECOND);
    }

    private boolean isValid(final String nonc) {

        Date now = new Date();

        if (now.after(this.getUnixEpochAsDate(this.expirationTime))
                || now.before(this.getUnixEpochAsDate(this.notBefore))) {
            return false;
        }

		if (!nonc.equals(this.getNonce())) {
            return false;
        }

        return true;
    }


    public static Token parseEncodedToken(final String encodedToken, final String nonce) {

        String[] tokenParts = encodedToken.split("\\.");
        String idToken = tokenParts[1];
        byte[] decodedBytes = Base64.getUrlDecoder().decode(idToken);
        ObjectMapper mapper = new ObjectMapper();
        Token newToken = null;
        try {
            newToken = mapper.readValue(decodedBytes, Token.class);
            if (!newToken.isValid(nonce)) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String val) {
        this.email = val;
    }
}