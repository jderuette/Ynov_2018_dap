package fr.ynov.dap.dap.services.microsoft;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.data.interfaces.TokenService;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * The Class MicrosoftService.
 */
@Service
public class MicrosoftService {
	
	@Autowired
	private Config cfg;
	
	private static String[] scopes = { "openid", "offline_access", "profile", "User.Read", "Mail.Read",
			"Calendars.Read", "Contacts.Read" };

	private static String appId = null;
	private static String appPassword = null;
	private static String redirectUrl = null;

	protected static String getAppId() {
		if (appId == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return appId;
	}

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

	protected static String getRedirectUrl() {
		if (redirectUrl == null) {
			try {
				loadConfig();
			} catch (Exception e) {
				return null;
			}
		}
		return redirectUrl;
	}

	protected static String getScopes() {
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
		InputStream authConfigStream = MicrosoftService.class.getClassLoader().getResourceAsStream(authConfigFile);

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
	 * Gets the token from auth code.
	 *
	 * @param authCode the auth code
	 * @param tenantId the tenant id
	 * @return the token from auth code
	 */
	public TokenResponse getTokenFromAuthCode(String authCode, String tenantId) {
		// Create a logging interceptor to log request and responses
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

		// Create and configure the Retrofit object
		Retrofit retrofit = new Retrofit.Builder().baseUrl(cfg.getAuthority()).client(client)
				.addConverterFactory(JacksonConverterFactory.create()).build();

		// Generate the token service
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
	 * @param tokens   the tokens
	 * @param tenantId the tenant id
	 * @return the token response
	 */
	public TokenResponse ensureTokens(TokenResponse tokens, String tenantId) {
		// Are tokens still valid?
		Calendar now = Calendar.getInstance();
		if (now.getTime().before(tokens.getExpirationTime())) {
			// Still valid, return them as-is
			return tokens;
		} else {
			// Expired, refresh the tokens
			// Create a logging interceptor to log request and responses
			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
			interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

			OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

			// Create and configure the Retrofit object
			Retrofit retrofit = new Retrofit.Builder().baseUrl(cfg.getAuthority()).client(client)
					.addConverterFactory(JacksonConverterFactory.create()).build();

			// Generate the token service
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
}