package fr.ynov.dap.services.microsoft;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.Config;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.data.interfaces.TokenService;
import fr.ynov.dap.utils.ExtendsUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * The Class MicrosoftService.
 */

@Service
public class MicrosoftService extends ExtendsUtils {

	/** The conf. */
	@Autowired
	private Config conf;

	/**
	 * Gets the conf.
	 *
	 * @return the conf
	 */
	public Config getConf() {
		return conf;
	}

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
	protected String getAppId() {
		if (appId == null) {
			try {
				loadConfig();
			} catch (Exception e) {
			    //TODO thb by Djer |log4J| Une petite log ?
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
	protected String getAppPassword() {
		if (appPassword == null) {
			try {
				loadConfig();
			} catch (Exception e) {
			    //TODO thb by Djer |log4J| Une petite log ?
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
	protected String getRedirectUrl() {
		if (redirectUrl == null) {
			try {
				loadConfig();
			} catch (Exception e) {
			    //TODO thb by Djer |log4J| Une petite log ?
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
	protected String getScopes() {
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
	protected void loadConfig() throws IOException {
	    //TODO thb by Djer |Design Patern| Externalisation de la configuration ?
		String authConfigFile = "auth.properties";
		InputStream authConfigStream = MicrosoftAccountService.class.getClassLoader()
				.getResourceAsStream(authConfigFile);

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
		Retrofit retrofit = new Retrofit.Builder().baseUrl(conf.getAuthority()).client(client)
				.addConverterFactory(JacksonConverterFactory.create()).build();

		// Generate the token service
		TokenService tokenService = retrofit.create(TokenService.class);

		try {
			return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(), "authorization_code",
					authCode, getRedirectUrl()).execute().body();
		} catch (IOException e) {
		  //TODO thb by Djer |log4J| Une petite log ?
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
			Retrofit retrofit = new Retrofit.Builder().baseUrl(conf.getAuthority()).client(client)
					.addConverterFactory(JacksonConverterFactory.create()).build();

			// Generate the token service
			TokenService tokenService = retrofit.create(TokenService.class);

			try {
				return tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(),
						"refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();
			} catch (IOException e) {
			  //TODO thb by Djer |log4J| Une petite log ?
				TokenResponse error = new TokenResponse();
				error.setError("IOException");
				error.setErrorDescription(e.getMessage());
				return error;
			}
		}
	}
}
