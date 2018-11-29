package fr.ynov.dap.service;

import java.io.IOException;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
/**
 * Utilisation du design pattern interceptor, et permet entre de faire correspondre le mail indiqué au token existant.
 * @author abaracas
 *
 */
public class OutlookServiceBuilder {
    private static Logger LOG = LogManager.getLogger();
    /**
     * Renvoie le bon OutlookService construit.
     * @param accessToken token de l'utilisateur
     * @param userEmail mail lié
     * @return le ServiceS
     */
	public static OutlookService getOutlookService(String accessToken, String userEmail) {
		// Create a request interceptor to add headers that belong on
		// every request
		Interceptor requestInterceptor = new Interceptor() {
			@Override
			public Response intercept(Interceptor.Chain chain) throws IOException {
				Request original = chain.request();
				Builder builder = original.newBuilder()
						.header("User-Agent", "java-tutorial")
						.header("client-request-id", UUID.randomUUID().toString())
						.header("return-client-request-id", "true")
						.header("Authorization", String.format("Bearer %s", accessToken))
						.method(original.method(), original.body());
				
				if (userEmail != null && !userEmail.isEmpty()) {
					builder = builder.header("X-AnchorMailbox", userEmail);
				}
				
				Request request = builder.build();
				LOG.debug("request vaut : " + request + " pour le userEmail : " + userEmail);
				return chain.proceed(request);			
			}
		};
				
		// Create a logging interceptor to log request and responses
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(requestInterceptor)
				.addInterceptor(loggingInterceptor)
				.build();
		
		// Create and configure the Retrofit object
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://outlook.office.com")
				.client(client)
				.addConverterFactory(JacksonConverterFactory.create())
				.build();
		
		// Generate the token service
		LOG.info("Bonne generation du Token Service pour le userEmail : " + userEmail);
		return retrofit.create(OutlookService.class);
	}
}
