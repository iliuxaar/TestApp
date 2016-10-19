package com.example.iliuxa.testapp.Activity;

import android.support.v7.app.AppCompatActivity;

import com.example.iliuxa.testapp.Service.ResultService;
import com.octo.android.robospice.SpiceManager;


public abstract class SpiceActivity extends AppCompatActivity {
    private SpiceManager spiceManager= new SpiceManager(ResultService.class);

    @Override
    protected void onStart(){
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop(){
        spiceManager.shouldStop();
        super.onStop();
    }


    protected SpiceManager getSpiceManager(){return spiceManager;}
}
