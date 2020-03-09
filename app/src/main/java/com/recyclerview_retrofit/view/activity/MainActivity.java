package com.recyclerview_retrofit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.recyclerview_retrofit.R;
import com.recyclerview_retrofit.model.DataModel;
import com.recyclerview_retrofit.presenter.ListPresenter;
import com.recyclerview_retrofit.view.adapter.DataListAdapter;
import com.recyclerview_retrofit.view.utils.NetworkCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_list;
    private NestedScrollView scrollView;
    private SwipeRefreshLayout swipe_refresh;

    private List<DataModel.Data> dataModels;
    private DataListAdapter dataListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_list = findViewById(R.id.rv_list);
        scrollView= findViewById(R.id.scrollView);
        swipe_refresh= findViewById(R.id.swipe_refresh);

        getListDetails();

    }

    private void getListDetails(){

        ListPresenter.getDataList(1000, new NetworkCallback() {
            @Override
            public void onSuccess(int resultCode, Object response) {
                Log.e("Success ::" , response.toString());
                dataModels = new ArrayList<>();
                DataModel dataModel = (DataModel) response;
                dataModels = dataModel.getData();

                dataListAdapter = new DataListAdapter(dataModels, MainActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                rv_list.setLayoutManager(mLayoutManager);
                rv_list.setAdapter(dataListAdapter);
            }

            @Override
            public void onFailure(int resultCode, Object response) {
                Log.e("onFailure ::" , new Gson().toJson(response));
            }

            @Override
            public void onError(int resultCode, Object response) {
                Log.e("onError ::" ,new Gson().toJson(response));
            }
        });

    }
}
