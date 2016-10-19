package com.example.iliuxa.testapp.DI;

import com.example.iliuxa.testapp.Model.ResultRequestForm;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class ResultRequestFormModule {
    @Provides
    @Singleton
    public ResultRequestForm provideResultRequestForm(String url){
        ResultRequestForm form = new ResultRequestForm();
        return form;
    }
}
