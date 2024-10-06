package com.ssuclass.networkconnection.networkservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KoreanPhraseApiClient {
    private static final String BASE_URL = "https://korean-advice-open-api.vercel.app/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
