package com.example.iliuxa.testapp.Application;

import android.app.Application;
import android.content.Context;

import com.example.iliuxa.testapp.DI.DaggerDynamicFormComponent;
import com.example.iliuxa.testapp.DI.DynamicFormComponent;
import com.example.iliuxa.testapp.DI.DynamicFormModule;
import com.example.iliuxa.testapp.DI.ResultRequestFormModule;


public class MyApp extends Application {
    DynamicFormComponent formComponent;

    public static DynamicFormComponent component(Context context){
        return ((MyApp)context.getApplicationContext()).formComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        formComponent = DaggerDynamicFormComponent
                .builder()
                .dynamicFormModule(new DynamicFormModule())
                .resultRequestFormModule(new ResultRequestFormModule())
                .build();
    }
}
