package com.example.iliuxa.testapp.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.iliuxa.testapp.Model.Form;
import com.example.iliuxa.testapp.Model.Type;
import com.example.iliuxa.testapp.R;

import java.util.ArrayList;


public class ViewHolderGenerator {
    private Context context;
    private ArrayList<RecyclerView.ViewHolder> holdersList;

    public ViewHolderGenerator(Context context){
        this.context = context;
        this.holdersList = new ArrayList<>();
    }

    public RecyclerView.ViewHolder getViewHolder(int position){
        return holdersList.get(position);
    }

    public int getHoldersCount(){
        return holdersList.size();
    }

    public RecyclerView.ViewHolder getViewHolderByType(int viewType, ViewGroup parent){
        RecyclerView.ViewHolder holder;
        switch (Type.values()[viewType]){
            case TEXT: holder = new HolderText(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_text,parent,false));break;
            case NUMERIC: holder = new HolderNumeric(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_numeric,parent,false));break;
            case LIST: holder = new HolderList(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_list,parent,false));break;
            default: holder = null;break;
        }
        holdersList.add(holder);
        return holder;
    }

    public void getViewHolderBindMethod(RecyclerView.ViewHolder holder, Form form){
        switch (holder.getItemViewType()){
            case 0: ((HolderText)holder).bind(form);break;
            case 1: ((HolderNumeric)holder).bind(form);break;
            case 2:
                ArrayAdapter adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,form.getValuesAsList()){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);
                        ((TextView) v).setTextColor(Color.BLACK);
                        return v;
                    }
                };
                ((HolderList)holder).bind(form,adapter);
                break;
        }
    }

    private class HolderText extends MyViewHolder {
        private final TextView title;
        private final EditText text;

        HolderText(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleText);
            text = (EditText) itemView.findViewById(R.id.textText);
        }

        public void bind(Form form){
            title.setText(form.getTitle());
            text.setHint("enter text");
        }

        @Override
        public String getData() {
            return text.getText().toString();
        }

        @Override
        public void setData(String data) {
            text.setText(data);
        }
    }

    private class HolderNumeric extends MyViewHolder {
        private final TextView title1;
        private final EditText numeric;

        HolderNumeric(View itemView) {
            super(itemView);
            title1 = (TextView) itemView.findViewById(R.id.titleText);
            numeric = (EditText) itemView.findViewById(R.id.numericText);
        }
        public void bind(Form form){
            title1.setText(form.getTitle());
            numeric.setHint("enter numbers");
        }

        @Override
        public String getData() {
            return numeric.getText().toString();
        }

        @Override
        public void setData(String data) {
            numeric.setText(data);
        }
    }

    private class HolderList extends MyViewHolder {
        private final TextView title;
        private final Spinner list;

        HolderList(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleText);
            list = (Spinner) itemView.findViewById(R.id.listView);
        }

        @Override
        public String getData() {
            return list.getSelectedItem().toString();
        }

        @Override
        public void setData(String data) {
            //todo:write there
        }

        void bind(Form form,ArrayAdapter adapter){
            title.setText(form.getTitle());
            list.setAdapter(adapter);

        }
    }

}
