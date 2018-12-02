package fr.ynov.dap.model;

import fr.ynov.dap.model.enumeration.CredentialEnum;


//TODO bal by Djer |POO| Bonne idée de créer un "DTO" pour regrouper Microsoft et Google. Tu aurais pu utiliser ton entité TokenResponse ? (il ne manque que le type Google/Microsoft) 
//TODO bal by Djer |JavaDoc| Documenter t'aurais peut-être aider à voir que cette classe ressemble enormement à ta TokenResponse ...
public class Credential {


    private String userId;

    private String token;

    private String refreshToken;

    private long expirationTime;

    private CredentialEnum type;

    //TODO bal by Djer |JavaDoc| Indiquer ici pourquoi il y a une valeur par defaut serait utile ("default to empty String for GoogleAccount compliance")
    private String tenantId = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String val) {
        this.userId = val;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String val) {
        this.token = val;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(final String val) {
        this.refreshToken = val;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(final long val) {
        this.expirationTime = val;
    }

    public CredentialEnum getType() {
        return type;
    }

    public void setType(final CredentialEnum val) {
        this.type = val;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(final String val) {
        this.tenantId = val;
    }

}
