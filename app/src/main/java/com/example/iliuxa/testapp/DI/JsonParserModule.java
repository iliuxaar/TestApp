package com.example.iliuxa.testapp.DI;

import com.example.iliuxa.testapp.Model.JsonParser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class JsonParserModule {

    @Provides
    @Singleton
    public JsonParser parsingToFromList(){
        JsonParser parser = new JsonParser();
        return parser;
    }
}
