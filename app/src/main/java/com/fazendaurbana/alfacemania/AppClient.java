package com.fazendaurbana.alfacemania;

import androidx.appcompat.app.AppCompatDelegate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClient {
    private static final String BASE_URL = "http://192.168.0.100:5236/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static AppCompatDelegate getInstance() {
        return null;
    }
}

