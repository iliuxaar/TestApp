package com.example.iliuxa.testapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class MyViewHolder extends RecyclerView.ViewHolder {
    MyViewHolder(View itemView) {
        super(itemView);
    }

    abstract public String getData();

    abstract public void setData(String data);
}
