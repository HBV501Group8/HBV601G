package com.example.hagspar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hagspar.forecast.ForecastCallback;
import com.example.hagspar.forecast.ForecastListAdapter;
import com.example.hagspar.forecast.ForecastManager;
import com.example.hagspar.networking.NetworkCallback;
import com.example.hagspar.networking.NetworkManager;
import com.example.hagspar.usermanagement.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class ForecastManagerActivity extends AppCompatActivity {

    ArrayList<String[]> mForecastList = new ArrayList<String[]>();
    User mUser;

    public static Intent newIntent(Context context, String username){
        Intent intent = new Intent(context, ForecastManagerActivity.class);
        intent.putExtra("username", username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_manager);

        Button genForecastBtn = (Button) findViewById(R.id.new_forecast_btn);
        genForecastBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(ForecastBuilderActivity.newIntent(ForecastManagerActivity.this, getIntent().getStringExtra("username")));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        ForecastManager forecastManager = ForecastManager.getInstance(this);
        forecastManager.getForecastList(getIntent().getStringExtra("username"), this,
                new ForecastCallback<ArrayList<String[]>>() {
                    @Override
                    public void whenReady(ArrayList<String[]> ready) {
                        drawList(ready);
                    }
                });
    }

    public void drawList (ArrayList<String[]> list){
        ForecastListAdapter forecastListAdapter = new ForecastListAdapter(list, this);
        ListView listView = (ListView)findViewById(R.id.forecastlist);
        listView.setAdapter(forecastListAdapter);
    }

    static public void viewForecast(String id){

    }


}