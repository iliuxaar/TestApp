package com.example.iliuxa.testapp.Request;

import com.example.iliuxa.testapp.Model.DynamicForm;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


public class DownloadSpiceRequest extends RetrofitSpiceRequest<DynamicForm,ResultPostRequest> {
    public DownloadSpiceRequest() {
        super(DynamicForm.class, ResultPostRequest.class);
    }

    @Override
    public DynamicForm loadDataFromNetwork() throws Exception {
        Thread.sleep(5000);
        return getService().getForms();
    }
}
