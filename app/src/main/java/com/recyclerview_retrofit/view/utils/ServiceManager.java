package com.recyclerview_retrofit.view.utils;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.recyclerview_retrofit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServiceManager {

    public  static <T> Observable<Response<Map>> callApiCMOL(@Nullable Class endpointClass, String methodName, T request, Class requestClass){
        if (!RetroFitHelper.isNetworkAvailable(RetrofitApplication.getRetrofitApp()))
        {
            Log.e("RetroFitHelper", "isNetworkAvailable");
            return Observable.error(new Exception(RetrofitApplication.getRetrofitApp().getResources().getString(R.string.network_connection_error)));
        }
        return  new RetroFitHelper(RetrofitApplication.getRetrofitApp().getResources().getString(R.string.BASE_URL)).call(endpointClass,methodName,request,requestClass)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void processResponse(Response<Map> response, int resultCode, Class responseType, NetworkCallback callback){
        Log.e("processResponse",""+response.code());
        if (response.code() == 200 || response.code() == 201) {
            if (responseType == String.class) {
                callback.onSuccess(resultCode, new Gson().toJson(response.body()));
                return;
            }
            String jsonString = new Gson().toJson(response.body());
            try {
                GsonBuilder builder = new GsonBuilder();
                builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
                Gson gson = builder.create();
                Object responseObject = gson.fromJson(jsonString, responseType);
                callback.onSuccess(resultCode, responseObject);

            } catch (Exception e) {
                callback.onFailure(resultCode, e.toString());
            }

        } else if (response.code() == 422 || response.code() == 403) {
            String jsonString;
            try {
                jsonString = response.errorBody().string();
                Object responseObject = new Gson().fromJson(jsonString, responseType);
                callback.onError(resultCode, responseObject);
            } catch (IOException e) {
                callback.onFailure(resultCode, "Unexpected error occurred");
            } catch (NullPointerException e) {
                callback.onFailure(resultCode, "Unexpected error occurred");
            }
        }else if (response.code() == 401) {
            String jsonString;
            try {
                jsonString = response.errorBody().string();
                JSONObject object = new JSONObject(jsonString);
                if (!object.has("error")) {
                    object.put("error", object.getString("message"));
                } else {
                    object.put("status", "error");
                }
                Object responseObject = new Gson().fromJson(object.toString(), responseType);
                callback.onError(resultCode, responseObject);
            } catch (IOException e) {
                callback.onFailure(resultCode, "Unexpected error occurred");
            } catch (NullPointerException e) {
                callback.onFailure(resultCode, "Unexpected error occurred");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (response.code() == 400) {
            String jsonString;
            try {
                jsonString = response.errorBody().string();
                Object responseObject = new Gson().fromJson(jsonString, responseType);
                callback.onError(resultCode, responseObject);
            } catch (IOException e) {
                callback.onFailure(resultCode, "Unexpected error occurred");
            } catch (NullPointerException e) {
                callback.onFailure(resultCode, "Unexpected error occurred");
            }
        } else {
            callback.onFailure(resultCode, "Unexpected error occurred");
        }
    }


}
