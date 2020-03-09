package com.recyclerview_retrofit.presenter;


import android.util.Log;

import com.google.gson.Gson;
import com.recyclerview_retrofit.model.DataModel;
import com.recyclerview_retrofit.view.utils.APIEndpoints;
import com.recyclerview_retrofit.view.utils.NetworkCallback;
import com.recyclerview_retrofit.view.utils.NetworkSubscriber;
import com.recyclerview_retrofit.view.utils.ServiceManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;

public class ListPresenter {

    public static void getDataList(int requestCode, final NetworkCallback callback) {
        rx.Observable<Response<Map>> observable = ServiceManager.callApiCMOL(APIEndpoints.class, "dataList");
        observable.subscribe(new NetworkSubscriber(new NetworkCallback() {
            @Override
            public void onSuccess(int resultCode, Object response) {
                Log.e("getData :onSuccess ::",new Gson().toJson(response));
                Response<Map> mapResponse = (Response<Map>) response;
                ServiceManager.processResponse(mapResponse, resultCode, DataModel.class, callback);

            }

            @Override
            public void onFailure(int resultCode, Object response) {
                Log.e("getData :onFailure ::",new Gson().toJson(response));
                callback.onFailure(resultCode, response);
            }

            @Override
            public void onError(int resultCode, Object response) {
                Log.e("getData :onError ::",new Gson().toJson(response));
                callback.onError(resultCode,response);
            }
        }, requestCode));
    }

}
