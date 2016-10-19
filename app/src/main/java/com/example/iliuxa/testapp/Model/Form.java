package com.example.iliuxa.testapp.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Form {
    private String title;
    private String name;
    private Type type;
    private HashMap<String, String> values;

    public List<String> getValuesAsList(){
        List<String> list = new ArrayList<>();
        Iterator iterator = values.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry value = (Map.Entry)iterator.next();
            list.add(value.getValue().toString());
        }
        return list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public HashMap<String, String> getValues() {
        return values;
    }

    public void setValues(HashMap<String, String> values) {
        this.values = values;
    }
}
