package com.isansc.retrofitpoc.controller.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class GithubController {
    private static final String URL_SERVER_API = "https://api.github.com/";

    private Retrofit mRetrofit;

    protected Retrofit getRetrofit() {
        return mRetrofit;
    }

    public GithubController(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Starting Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_SERVER_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}