package com.example.iliuxa.testapp.Service;

import com.example.iliuxa.testapp.Request.ResultPostRequest;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;


public class ResultService extends RetrofitGsonSpiceService{
    private final static String BASE_URL = "http://test.clevertec.ru";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(ResultPostRequest.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }
}
