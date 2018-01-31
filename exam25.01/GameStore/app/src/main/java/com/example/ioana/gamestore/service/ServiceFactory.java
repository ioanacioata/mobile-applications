package com.example.ioana.gamestore.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ioana on 30/01/2018.
 */


/**
 * Creates a Retrofit that turns the HTTP API into a given Java Interface (in this case it will
 * be the Service interface).
 * It automatically transforms the JSON into object.
 */
public class ServiceFactory {
    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(endPoint)
                .build();
        return retrofit.create(clazz);
    }
}
