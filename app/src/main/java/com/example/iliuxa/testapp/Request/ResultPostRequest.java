package com.example.iliuxa.testapp.Request;

import com.example.iliuxa.testapp.Model.DynamicForm;
import com.example.iliuxa.testapp.Model.OperationResult;
import com.example.iliuxa.testapp.Model.ResultRequestForm;

import retrofit.http.Body;
import retrofit.http.POST;



public interface ResultPostRequest {
    @POST("/tt/data/")
    OperationResult createTask(@Body ResultRequestForm result);
    @POST("/tt/meta")
    DynamicForm getForms();
}
