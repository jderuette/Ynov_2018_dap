package fr.ynov.dap.client.service;

import java.io.IOException;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import fr.ynov.dap.client.IApiResponse;

/**
 * .
 * @author Dom
 *
 */
public class RequestHttpService {
    /**
     * .
     * @param callback .
     * @param paramUrl .
     * @return .
     * @throws IOException .
     */
    public Response executeServiceGet(final IApiResponse callback, final String paramUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(paramUrl).newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(final Request request, final IOException e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                callback.onSuccess(response.body().string());
            }

        });
        return response;

    }

}
