package com.recyclerview_retrofit.view.utils;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class RetrofitApplication extends MultiDexApplication {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
    }

    public static RetrofitApplication getRetrofitApp() {
        return (RetrofitApplication) mApplicationContext;
    }
}
