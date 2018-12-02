package fr.ynov.dap.microsoft;

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


//TODO bal by Djer |JavaDoc| Expliquer l'objectif de cette classe serait bienvenu.
public class GetOutlookService {
    //TODO bal by Djer |POO| Pourquoi static ?
	public static OutlookGetService getOutlookService(final String accessToken, final String userEmail) {

        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(final Interceptor.Chain chain) throws IOException {
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
        return retrofit.create(OutlookGetService.class);

	}
}
