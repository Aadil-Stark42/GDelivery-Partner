package com.vdeliverz_delivery.retrofit;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import com.vdeliverz_delivery.BuildConfig;
import com.vdeliverz_delivery.utils.MnxConstant;
import com.vdeliverz_delivery.utils.MnxPreferenceManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VdeliverzApi {

    private static Retrofit retrofit = null;

    //Live URL
    public static final String BASE_URL = "http://dev.gdigitaldelivery.com/api/v2/";

     static String TAG = VdeliverzApi.class.getSimpleName();

    public static VdeliverzApiInterface getClient() {

        Log.d(TAG, "getClient: 0");

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.level(HttpLoggingInterceptor.Level.NONE);
        }

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {

            @NotNull
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();

                Log.d(TAG, "intercept: ");

                String token = MnxPreferenceManager.getString(MnxConstant.TOKEN, "");

                if (token != null) {
                    Log.d("RetrofitClient", "intercept: " + "Bearer" + token);
                    requestBuilder.addHeader("Authorization", "Bearer" + " " + token);
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Log.d(TAG, "getClient: 3");

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        VdeliverzApiInterface api = retrofit.create(VdeliverzApiInterface.class);
        return api;
    }


}
