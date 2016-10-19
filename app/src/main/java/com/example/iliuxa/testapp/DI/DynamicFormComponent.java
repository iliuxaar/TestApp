package com.example.iliuxa.testapp.DI;


import com.example.iliuxa.testapp.Activity.MainActivity;
import com.example.iliuxa.testapp.Model.DynamicForm;
import com.example.iliuxa.testapp.Model.JsonParser;
import com.example.iliuxa.testapp.Model.ResultRequestForm;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        DynamicFormModule.class,
        JsonParserModule.class,
        ResultRequestFormModule.class
})
public interface DynamicFormComponent {
    void inject(MainActivity mainActivity);
    DynamicForm getDynamicFormObject();
    JsonParser getJsonParserObject();
    ResultRequestForm getResultRequestFormObeject();
}
