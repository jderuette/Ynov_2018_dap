package com.ynov.dap.service.microsoft;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * The Class OutlookServiceBuilder.
 */
public class OutlookServiceBuilder {

	/**
	 * Gets the outlook service.
	 *
	 * @param accessToken the access token
	 * @param userEmail the user email
	 * @return the outlook service
	 */
	public static OutlookService getOutlookService(String accessToken, String userEmail) {
		Interceptor requestInterceptor = new Interceptor() {
			@Override
			public Response intercept(Interceptor.Chain chain) throws IOException {
				Request original = chain.request();
				Builder builder = original.newBuilder().header("User-Agent", "java-tutorial")
						.header("client-request-id", UUID.randomUUID().toString())
						.header("return-client-request-id", "true")
						.header("Authorization", String.format("Bearer %s", accessToken))
						.method(original.method(), original.body());

				Request request = builder.build();
				return chain.proceed(request);
			}
		};

		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(requestInterceptor)
				.addInterceptor(loggingInterceptor).build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl("https://graph.microsoft.com").client(client)
				.addConverterFactory(JacksonConverterFactory.create()).build();

		return retrofit.create(OutlookService.class);
	}
}