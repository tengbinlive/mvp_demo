package com.boqii.plant.api.helper;

import com.boqii.plant.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.covert.fastjson.FastJsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    private RetrofitClient() {

    }

    private static void create() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_HOST)
                .client(CustomerOkHttpClient.getClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            create();
        }
        return retrofit;
    }
}
