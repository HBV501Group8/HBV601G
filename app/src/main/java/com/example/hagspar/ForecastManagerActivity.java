package com.example.hagspar;

import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.hagspar.forecast.ForecastListAdapter;
import com.example.hagspar.networking.NetworkCallback;
import com.example.hagspar.networking.NetworkManager;

import java.util.ArrayList;


public class ForecastManagerActivity extends AppCompatActivity {

    ArrayList<String[]> mForecastList = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_manager);

        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.getForecastList(new NetworkCallback<ArrayList<String[]>>() {

            @Override
            public void onSuccess(ArrayList<String[]> result) {
                mForecastList = result;
                drawList(mForecastList);
            }

            @Override
            public void onFail(String errorString) {
                Log.e("test", errorString);
                //TODO Toast not found
            }
        });
    }


    private void drawList (ArrayList<String[]> list){
        ForecastListAdapter forecastListAdapter = new ForecastListAdapter(list, this);
        ListView listView = (ListView)findViewById(R.id.forecastlist);
        listView.setAdapter(forecastListAdapter);
    }

    static public void viewForecast(String id){

    }


}