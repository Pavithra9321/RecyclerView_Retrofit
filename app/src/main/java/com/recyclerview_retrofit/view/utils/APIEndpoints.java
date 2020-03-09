package com.recyclerview_retrofit.view.utils;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

public interface APIEndpoints {

    @GET("employees")
    Observable<Response<Map>> dataList();
}
