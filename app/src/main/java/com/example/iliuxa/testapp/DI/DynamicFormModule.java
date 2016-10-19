package com.example.iliuxa.testapp.DI;

import com.example.iliuxa.testapp.Model.DynamicForm;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class DynamicFormModule {
    @Provides
    @Singleton
    public DynamicForm provideDynamicForm(String url){
        DynamicForm form = new DynamicForm();
        //form.setUrl(url);
        return form;
    }

    @Provides
    public String provideUrl(){
        return "http://test.clevertec.ru/tt/meta";
    }

}
