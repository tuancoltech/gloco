package com.gloco.exercise.data.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.gloco.exercise.BuildConfig;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsRequestModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsResponseModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationRequestModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationResponseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by "Tuan Nguyen" on 11/10/2016.
 */

public class ApiManager {


    private static final String BASE_URL = "https://private-96581-glocoexercise.apiary-mock.com";

    private static final String TAG = ApiManager.class.getSimpleName();

    private static ApiEndpointInterface mApiEndpoint;

    private static volatile Retrofit mRetrofit;

    /**
     * Use this method to obtaining Retrofit instance
     *
     * @param context The context where Retrofit instance is used
     * @return a singleton instance of Retrofit
     */
    private static Retrofit getRetrofit(final Context context) {
        if (mRetrofit == null) {
            synchronized (ApiManager.class) {
                if (mRetrofit == null) {
                    //Set log
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    final boolean isLog = BuildConfig.DEBUG;
                    logging.setLevel(isLog ? HttpLoggingInterceptor.Level.BODY :
                            HttpLoggingInterceptor.Level.NONE);
                    //Create cache
                    File file = new File(context.getCacheDir(), "response");

                    //Add log and set time out
                    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .readTimeout(60, TimeUnit.SECONDS)
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .cache(new Cache(file, 10 * 1024 * 1024)) //10 MB
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request();

                                    if (request.method().equals("GET")) {
                                        if (NetworkUtils.isNetworkAvailable(context)) {
                                            request = request.newBuilder().header
                                                    ("Cache-Control", "public, max-age=" + 60)
                                                    .build();
                                        } else {
                                            request = request.newBuilder().header
                                                    ("Cache-Control", "public, only-if-cached, " +
                                                            "max-stale=" + 60 * 60 * 24 * 28)
                                                    .build();
                                        }
                                    }
                                    return chain.proceed(request);
                                }
                            })
                            .addInterceptor(logging)
                            .addNetworkInterceptor(new StethoInterceptor())
                            .build();

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(okHttpClient).build();
                }
            }
        }

        return mRetrofit;
    }

    /**
     * Use this method to obtain the Api endpoint instance where Retrofit request definitions are
     * declared
     *
     * @param context The context where APIs are consumed
     * @return The Api endpoint instance to access network requests
     */
    private static ApiEndpointInterface getApiEndpoint(Context context) {
        if (mApiEndpoint == null) {
            synchronized (ApiManager.class) {
                if (mApiEndpoint == null) {
                    mApiEndpoint = getRetrofit(context).create(ApiEndpointInterface.class);
                }
            }
        }
        return mApiEndpoint;
    }

    /**
     * This is for calling getLocation API to retrieve location coordinates
     *
     * @param context                          Where location should be gotten
     * @param getLocationRequestModel          The request param of this API
     * @param getLocationResponseModelCallback The response callback upon completing of API
     *                                         execution
     */
    public static void getLocation(@NonNull Context context,
                                   @NonNull GetLocationRequestModel getLocationRequestModel,
                                   Callback<GetLocationResponseModel>
                                           getLocationResponseModelCallback) {

        Call<GetLocationResponseModel> call = getApiEndpoint(context).getLocation
                (getLocationRequestModel);
        call.enqueue(getLocationResponseModelCallback);

    }

    /**
     * This is for calling getDoctors API to retrieve doctors list
     *
     * @param context                         Where doctors should be retrieved
     * @param getDoctorsRequestModel          The param of getDoctors API
     * @param getDoctorsResponseModelCallback The response callback
     */
    public static void getDoctors(@NonNull Context context,
                                  @NonNull GetDoctorsRequestModel getDoctorsRequestModel,
                                  Callback<GetDoctorsResponseModel>
                                          getDoctorsResponseModelCallback) {

        Call<GetDoctorsResponseModel> call = getApiEndpoint(context).getDoctors
                (getDoctorsRequestModel);
        call.enqueue(getDoctorsResponseModelCallback);

    }

}
