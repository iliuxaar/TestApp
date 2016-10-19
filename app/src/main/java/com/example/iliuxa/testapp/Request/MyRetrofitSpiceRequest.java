package com.example.iliuxa.testapp.Request;

import com.example.iliuxa.testapp.Model.OperationResult;
import com.example.iliuxa.testapp.Model.ResultRequestForm;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


public class MyRetrofitSpiceRequest extends RetrofitSpiceRequest<OperationResult,ResultPostRequest> {
    private ResultRequestForm requestForm;

    public MyRetrofitSpiceRequest(ResultRequestForm resultRequestForm) {
        super(OperationResult.class, ResultPostRequest.class);
        this.requestForm = resultRequestForm;
    }

    @Override
    public OperationResult loadDataFromNetwork() throws Exception {
        Thread.sleep(5000);
        return getService().createTask(requestForm);
    }
}
