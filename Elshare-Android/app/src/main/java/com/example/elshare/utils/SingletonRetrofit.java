package com.example.elshare.utils;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.example.elshare.landing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import datamodel.APIInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

final public class SingletonRetrofit {
    public static Retrofit retrofit;
    public static final String BASE_URL = "http://elshare.in/api/";

    synchronized public static final Retrofit getRetrofit(){
        if(retrofit == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new ChuckerInterceptor(landing.applicationContext))
                    .build();


            Gson gson = new GsonBuilder().serializeNulls().create();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://elshare.in/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    synchronized public static final Retrofit getGoogleMapRetrofit(String baseUrl) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new ChuckerInterceptor(landing.applicationContext))
                    .build();

            Gson gson = new GsonBuilder().serializeNulls().create();

            Retrofit googleMapretrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        return googleMapretrofit;
    }

    synchronized public static final APIInterface getAPIInterface(){
        return getRetrofit().create(APIInterface.class);
    }
}