package org.kwork4.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static Retrofit retrofit;
    private static OkHttpClient client;
    private static int REQUEST_TIMEOUT = 30;
    private static API api;

    public static Retrofit getRetrofitInstance() {
        if(retrofit!=null) return retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.airtable.com/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
        return retrofit;
    }

    public static OkHttpClient getClient() {
        if(client!=null) return client;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Request.Builder builder = request.newBuilder();
                    builder
                            .addHeader("accept", "application/json")
                            .addHeader("content-type", "application/json")
                            .addHeader("authorization","Bearer keynjwqezU6lOzOEt");
                    return chain.proceed(builder.build());
                })
                .build();
        return client;
    }
    public static API getApi() {
        if(api==null) api = getRetrofitInstance().create(API.class);
        return api;
    }
}
