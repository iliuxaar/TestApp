package com.example.iliuxa.testapp.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.iliuxa.testapp.Adapter.MyRecycleViewAdapter;
import com.example.iliuxa.testapp.Application.MyApp;
import com.example.iliuxa.testapp.DI.DynamicFormComponent;
import com.example.iliuxa.testapp.Model.DynamicForm;
import com.example.iliuxa.testapp.Model.OperationResult;
import com.example.iliuxa.testapp.Model.ResultRequestForm;
import com.example.iliuxa.testapp.R;
import com.example.iliuxa.testapp.Request.DownloadSpiceRequest;
import com.example.iliuxa.testapp.Request.MyRetrofitSpiceRequest;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends SpiceActivity {
    private final String CAHCHE_DOWNLOAD = "download request";
    private final String CAHCHE_RESULT = "result request";
    @Inject
    DynamicForm form;
    private ResultRequestForm resultRequestForm;
    private FloatingActionButton sendButton;
    private MyRecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private ResultRequestListener resultRequestListener;
    private DownloadRequestListener myRequestListener;
    private boolean isWasPending = false;
    private boolean isFormAdded = false;
    private Button downloadButton;
    private ProgressDialog dialog;
    private ArrayList<String> savedArray;
    private boolean isDownloadRequest = false;
    private boolean isResultRequest = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElements();
        myRequestListener = new DownloadRequestListener();
        resultRequestListener = new ResultRequestListener();
        DynamicFormComponent component = MyApp.component(this);
        component.inject(this);
        resultRequestForm = component.getResultRequestFormObeject();
        getSpiceManager().removeAllDataFromCache();
    }

    private void initElements(){
        downloadButton = (Button)findViewById(R.id.downloadButton);
        sendButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        sendButton.setVisibility(View.GONE);
    }

    private void setButtonsListeners(){
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultRequestForm.setForm(adapter.getData());
                if(resultRequestForm.isFieldsEmpty()){
                    hideKeyboard();
                    Toast.makeText(getApplicationContext(),"Please, enter all fields",Toast.LENGTH_LONG).show();
                }
                else {
                    hideKeyboard();
                    isResultRequest = true;
                    ResultRequestListener resultRequest = new ResultRequestListener();
                    MyRetrofitSpiceRequest request = new MyRetrofitSpiceRequest(resultRequestForm);
                    openProgressDialog(request);
                    getSpiceManager().removeDataFromCache(OperationResult.class,CAHCHE_RESULT);
                    getSpiceManager().addListenerIfPending(OperationResult.class,CAHCHE_RESULT,resultRequest);
                    getSpiceManager().execute(request,CAHCHE_RESULT,DurationInMillis.ONE_MINUTE * 5,resultRequest);
                }

            }
        });
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDownloadRequest = true;
                DownloadSpiceRequest request = new DownloadSpiceRequest();
                getSpiceManager().addListenerIfPending(DynamicForm.class,CAHCHE_DOWNLOAD,myRequestListener);
                getSpiceManager().execute(request,CAHCHE_DOWNLOAD, DurationInMillis.ONE_MINUTE * 5,
                        myRequestListener);
                openProgressDialog(request);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        setButtonsListeners();
  }

    @Override
    protected void onResume() {
        super.onResume();

        if(isDownloadRequest){
            openProgressDialog(new DownloadSpiceRequest());
            getSpiceManager().addListenerIfPending(DynamicForm.class,CAHCHE_RESULT,myRequestListener);
        }
        if(isResultRequest){
            openProgressDialog(new MyRetrofitSpiceRequest(resultRequestForm));
            getSpiceManager().addListenerIfPending(OperationResult.class,CAHCHE_RESULT,resultRequestListener);
        }
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                getApplicationContext());
        quitDialog.setTitle("Выход: Вы уверены?");
        quitDialog.setPositiveButton("Выйти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        quitDialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        quitDialog.show();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
    }

    private void openProgressDialog(final SpiceRequest request){
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle(getString(R.string.dialog_downloading));
        dialog.setMessage(getString(R.string.dialog_waiting));
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSpiceManager().cancel(request);
            }
        });
        dialog.show();
    }

    public final class DownloadRequestListener implements PendingRequestListener<DynamicForm>,RequestProgressListener, RequestListener<DynamicForm> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MainActivity.this,"Error. Please, check internet access!",Toast.LENGTH_SHORT).show();
            dialog.cancel();
        }

        @Override
        public void onRequestSuccess(DynamicForm s) {
            form = s;
            recyclerView = (RecyclerView)findViewById(R.id.myList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(true);
            adapter = new MyRecycleViewAdapter(getApplicationContext(),form.getFields());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            sendButton.setVisibility(View.VISIBLE);
            downloadButton.setVisibility(View.GONE);
            isFormAdded = true;
            if(dialog != null)
                dialog.cancel();

            isDownloadRequest = false;
            if(isWasPending){
                //adapter.setData(savedArray);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onRequestNotFound() {

        }

        @Override
        public void onRequestProgressUpdate(RequestProgress progress) {

        }
    }

     public class ResultRequestListener implements PendingRequestListener<OperationResult>{

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MainActivity.this, R.string.request_errors,Toast.LENGTH_LONG).show();
            dialog.cancel();
        }

        @Override
        public void onRequestSuccess(OperationResult operationResult) {
            if(dialog != null)
                dialog.cancel();
            String bla = operationResult.getResult();
            Toast.makeText(MainActivity.this,bla,Toast.LENGTH_LONG).show();
            isResultRequest = false;
        }

        @Override
        public void onRequestNotFound() {

        }

    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(adapter!=null)
            outState.putStringArrayList("form",new ArrayList<String>(adapter.getData().values()));
        outState.putBoolean("rotation",isWasPending = true);
        outState.putBoolean("formAdded",isFormAdded);
        outState.putBoolean("downloadRequest", isDownloadRequest);
        outState.putBoolean("resultRequest", isResultRequest);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isWasPending = savedInstanceState.getBoolean("rotation");
        savedArray = savedInstanceState.getStringArrayList("form");
        isFormAdded = savedInstanceState.getBoolean("formAdded");
        if(isFormAdded) {
            downloadButton.setVisibility(View.GONE);
            getSpiceManager().getFromCache(DynamicForm.class,CAHCHE_DOWNLOAD,DurationInMillis.ONE_MINUTE*5,myRequestListener);
        }
        isDownloadRequest = savedInstanceState.getBoolean("downloadRequest");
        isResultRequest = savedInstanceState.getBoolean("resultRequest");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
