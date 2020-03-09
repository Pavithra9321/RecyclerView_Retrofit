package com.recyclerview_retrofit.view.utils;

import android.util.Log;

import java.util.Map;

import retrofit2.Response;
import rx.Subscriber;
import rx.exceptions.OnErrorFailedException;

public class NetworkSubscriber extends Subscriber<Response<Map>> {
    private NetworkCallback callback;
    private int resultCode;
    public NetworkSubscriber(NetworkCallback callback,int requestCode)
    {
        this.callback=callback;
        this.resultCode=requestCode;
    }
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable throwable) {
        try {
            if(throwable instanceof OnErrorFailedException) {
                throwable.printStackTrace();
            } else {
                callback.onFailure(resultCode,throwable.getMessage()==null||throwable.getMessage().equals("")?"Network Problem":throwable.getMessage());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onNext(Response response) {
        try {
            Log.e("response.message() ::", response.message());
            Log.e("response.url() ::",response.raw().request().url().toString());
            Log.e("response.body() ::",response.body().toString());
        } catch (Exception e) {
        }
        callback.onSuccess(resultCode,response);
    }
}
