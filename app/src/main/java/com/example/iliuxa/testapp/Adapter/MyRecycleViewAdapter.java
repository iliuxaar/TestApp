package com.example.iliuxa.testapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.iliuxa.testapp.Model.Form;
import com.example.iliuxa.testapp.Model.Type;
import com.example.iliuxa.testapp.ViewHolder.MyViewHolder;
import com.example.iliuxa.testapp.ViewHolder.ViewHolderGenerator;

import java.util.ArrayList;
import java.util.HashMap;


public class MyRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Form[] forms;
    private ViewHolderGenerator viewHolderGenerator;

    public MyRecycleViewAdapter(Context context, Form[] form){
        this.context = context;
        this.forms = form;
        viewHolderGenerator = new ViewHolderGenerator(context);
    }

    @Override
    public int getItemViewType(int position) {
        switch (forms[position].getType()){
            case TEXT: return Type.TEXT.ordinal();
            case LIST: return Type.LIST.ordinal();
            case NUMERIC: return Type.NUMERIC.ordinal();
            default:return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewHolderGenerator.getViewHolderByType(viewType, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewHolderGenerator.getViewHolderBindMethod(holder, forms[position]);
    }

    @Override
    public int getItemCount() {
        return forms.length;
    }



    public HashMap<String,String> getData(){
        HashMap<String,String> resultMap = new HashMap<>();
        for(int i = 0; i < getItemCount(); i++){
            resultMap.put(forms[i].getName(), ((MyViewHolder)viewHolderGenerator.getViewHolder(i)).getData());
        }
        return resultMap;
    }

    public void setData(ArrayList<String> data){
        for (int i = 0; i < getItemCount(); i++) {
            ((MyViewHolder) viewHolderGenerator.getViewHolder(i)).setData(data.get(i));
        }
    }

}
