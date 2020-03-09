package com.recyclerview_retrofit.view.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.recyclerview_retrofit.BuildConfig;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RetroFitHelper {
    private  Retrofit.Builder builder;
    public RetroFitHelper(String base_url)
    {
        builder = RetroFitHelper(base_url,false,null,null,null,null,null);
    }

    Retrofit.Builder RetroFitHelper(String base_url, final boolean isRemoveContentTransferEncoding, @Nullable final String apiVersion, @Nullable final String authorizationValue, @Nullable final String displayType, @Nullable final String source, @Nullable final String mode){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request request;
                        Request.Builder builder = chain.request().newBuilder();
                        request = builder.build();
                        return chain.proceed(request);
                    }
                }).addNetworkInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl(base_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    public <T,E> Observable<Response<Map>> call(Class classname, String methodname, T parameter, Class inputClass)  {
        Retrofit retrofit = builder.build();
        Object endPoints = retrofit.create(classname);
        Method methodObj;
        Log.e("parameter",new Gson().toJson(parameter) );
        try {
            methodObj = endPoints.getClass().getDeclaredMethod(methodname,inputClass);
            Log.e("methodObj",""+methodObj.toString());
            return (Observable<Response<Map>>) methodObj.invoke(endPoints,parameter);
        } catch (NoSuchMethodException e) {
            Log.e("NoSuchMethodException",""+e.toString());
            return Observable.error(new Exception(""));
        } catch (IllegalAccessException e) {
            Log.e("IllegalAccessException",""+e.toString());
            return Observable.error(new Exception(""));
        } catch (InvocationTargetException e) {
            Log.e("InvocationException",""+e.toString());
            return Observable.error(new Exception(""));
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

