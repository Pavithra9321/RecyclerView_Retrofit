package com.recyclerview_retrofit.view.utils;

public interface NetworkCallback {
    void onSuccess(int resultCode, Object response);
    void onFailure(int resultCode, Object response);
    void onError(int resultCode, Object response);
}
