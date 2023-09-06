package com.ridzi.pokemon1;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder()
                    .url(reqUrl)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }

            return response.body().string();
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            return null;
        }
    }
}
