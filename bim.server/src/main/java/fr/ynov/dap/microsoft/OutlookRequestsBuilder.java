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

/**
 * Builder.
 * @author MBILLEMAZ
 *
 */
//TODO bim by Djer |Audit Code| Traite la remarque de ton, outil d'audit. Pourrait etre un Service (singleton) et injecté dans les services en ayant besoin. Ou une classe mère de tous les servie "Microsoft"
public class OutlookRequestsBuilder {

    /**
     * get service.
     * @param accessToken token
     * @return outlook service
     */
    public static OutlookApiRequests getOutlookService(final String accessToken) {
        // Create a request interceptor to add headers that belong on
        // every request
        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(final Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Builder builder = original.newBuilder().header("User-Agent", "dap")
                        .header("client-request-id", UUID.randomUUID().toString())
                        .header("return-client-request-id", "true")
                        .header("Authorization", String.format("Bearer %s", accessToken))
                        .method(original.method(), original.body());

                Request request = builder.build();
                return chain.proceed(request);
            }
        };

        // Create a logging interceptor to log request and responses
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(requestInterceptor)
                .addInterceptor(loggingInterceptor).build();

        // Create and configure the Retrofit object
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://graph.microsoft.com").client(client)
                .addConverterFactory(JacksonConverterFactory.create()).build();

        // Generate the token service
        return retrofit.create(OutlookApiRequests.class);
    }
}