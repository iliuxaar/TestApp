package com.example.iliuxa.testapp.Model;

/**
 * Created by Iliuxa on 15.10.16.
 */

public class DynamicForm {
    private String title;
    private Form[] fields;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Form[] getFields() {
        return fields;
    }

    public Form getForm(int position){return fields[position];}

    public void setFields(Form[] fields) {
        this.fields = fields;
    }

}
