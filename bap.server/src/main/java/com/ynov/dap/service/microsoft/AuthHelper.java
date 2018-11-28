package com.ynov.dap.service.microsoft;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

import com.ynov.dap.model.microsoft.TokenResponse;
import com.ynov.dap.service.BaseService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * The Class AuthHelper.
 */
public class AuthHelper extends BaseService {

	/** The Constant authority. */
	private static final String authority = "https://login.microsoftonline.com";

	/** The Constant authorizeUrl. */
	private static final String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";

	/** The scopes. */
	private static String[] scopes = { "openid", "offline_access", "profile", "User.Read", "Mail.Read",
			"Calendars.Read", "Contacts.Read" };

	/** The app id. */
	private static String appId = null;

	/** The app password. */
	private static String appPassword = null;

	/** The redirect url. */
	private static String redirectUrl = null;

	/**
	 * Gets the app id.
	 *
	 * @return the app id
	 */
	private static String getAppId() {
		if (appId == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return appId;
	}

	/**
	 * Gets the app password.
	 *
	 * @return the app password
	 */
	private static String getAppPassword() {
		if (appPassword == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return appPassword;
	}

	/**
	 * Gets the redirect url.
	 *
	 * @return the redirect url
	 */
	private static String getRedirectUrl() {
		if (redirectUrl == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return redirectUrl;
	}

	/**
	 * Gets the scopes.
	 *
	 * @return the scopes
	 */
	private static String getScopes() {
		StringBuilder sb = new StringBuilder();
		for (String scope : scopes) {
			sb.append(scope + " ");
		}
		return sb.toString().trim();
	}

	/**
	 * Load config.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void loadConfig() throws IOException {
		String authConfigFile = "auth.properties";
		InputStream authConfigStream = AuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);

		if (authConfigStream != null) {
			Properties authProps = new Properties();
			try {
				authProps.load(authConfigStream);
				appId = authProps.getProperty("appId");
				appPassword = authProps.getProperty("appPassword");
				redirectUrl = authProps.getProperty("redirectUrl");
			} finally {
				authConfigStream.close();
			}
		} else {
			throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
		}
	}

	/**
	 * Gets the login url.
	 *
	 * @param state the state
	 * @param nonce the nonce
	 * @return the login url
	 */
	public static String getLoginUrl(UUID state, UUID nonce) {

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(authorizeUrl);
		urlBuilder.queryParam("client_id", getAppId());
		urlBuilder.queryParam("redirect_uri", getRedirectUrl());
		urlBuilder.queryParam("response_type", "code id_token");
		urlBuilder.queryParam("scope", getScopes());
		urlBuilder.queryParam("state", state);
		urlBuilder.queryParam("nonce", nonce);
		urlBuilder.queryParam("response_mode", "form_post");

		return urlBuilder.toUriString();
	}

	/**
	 * Gets the token from auth code.
	 *
	 * @param authCode the auth code
	 * @param tenantId the tenant id
	 * @return the token from auth code
	 */
	public static TokenResponse getTokenFromAuthCode(String authCode, String tenantId) {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(authority).client(client)
				.addConverterFactory(JacksonConverterFactory.create()).build();

		TokenService tokenService = retrofit.create(TokenService.class);

		try {
			return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(), "authorization_code",
					authCode, getRedirectUrl()).execute().body();
		} catch (IOException e) {
			TokenResponse error = new TokenResponse();
			error.setError("IOException");
			error.setErrorDescription(e.getMessage());
			return error;
		}
	}

	/**
	 * Ensure tokens.
	 *
	 * @param tokens the tokens
	 * @param tenantId the tenant id
	 * @return the token response
	 */
	public static TokenResponse ensureTokens(TokenResponse tokens, String tenantId) {
		Calendar now = Calendar.getInstance();
		if (now.getTime().before(tokens.getExpirationTime())) {
			return tokens;
		} else {
			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
			interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

			OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

			Retrofit retrofit = new Retrofit.Builder().baseUrl(authority).client(client)
					.addConverterFactory(JacksonConverterFactory.create()).build();

			TokenService tokenService = retrofit.create(TokenService.class);

			try {
				return tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(),
						"refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();
			} catch (IOException e) {
				TokenResponse error = new TokenResponse();
				error.setError("IOException");
				error.setErrorDescription(e.getMessage());
				return error;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.ynov.dap.service.BaseService#getClassName()
	 */
	@Override
	protected String getClassName() {
		return AuthHelper.class.getName();
	}
}