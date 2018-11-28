package fr.ynov.dap.dap.model;

public class CredentialModel {

	private String user;

	private String access_token;

	private String refreshToken;

	private String type;

	private String tenantId;

	public CredentialModel(String email, String access_token, String type, String tenantId, String refreshToken) {
		this.user = email;
		this.access_token = access_token;
		this.type = type;
		this.tenantId = tenantId;
		this.refreshToken = refreshToken;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
