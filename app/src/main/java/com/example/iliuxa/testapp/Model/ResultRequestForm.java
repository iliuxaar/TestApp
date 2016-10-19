package com.example.iliuxa.testapp.Model;

import java.util.ArrayList;
import java.util.HashMap;


public class ResultRequestForm {
    private HashMap<String,String> form;

    public HashMap<String, String> getForm() {
        return form;
    }

    public void setForm(HashMap<String, String> form) {
        this.form = form;
    }

    public boolean isFieldsEmpty(){
        ArrayList<String> temp = new ArrayList<String>( form.values());
        for(int i = 0; i < form.size(); i++){
            if(temp.get(i).isEmpty()){
                return true;
            }
        }
        return false;
    }
}
