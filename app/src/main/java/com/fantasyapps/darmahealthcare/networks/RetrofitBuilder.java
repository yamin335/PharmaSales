package com.fantasyapps.darmahealthcare.networks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fantasyapps.darmahealthcare.constants.UrlConstants.BASE_URL;

public class RetrofitBuilder {
    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private RetrofitBuilder() {
    }

    // builds OkHttpClient with logging Interceptor
    private static OkHttpClient buildClient() {
        return new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static Retrofit buildRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitBuilder.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .client(buildClient())
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
        }

        return retrofit;
    }

}