package com.example.iliuxa.testapp.Model;


import com.google.gson.Gson;

public class JsonParser {
    public Object parsing(String resultJson, Object object){
        Gson gson = new Gson();
        object = gson.fromJson(resultJson,object.getClass());
        return object;
    }
}
