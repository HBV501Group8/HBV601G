package com.example.hagspar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.hagspar.forecast.ForecastManager;
import com.example.hagspar.adapters.ForecastViewAdapter;
import com.example.hagspar.forecast.data.Forecast;
import com.example.hagspar.forecast.data.ForecastResult;
import com.google.android.material.tabs.TabLayout;

public class ViewForecastActivity extends AppCompatActivity {

    private static Forecast mForecast;

    TabLayout tabLayout;
    ViewPager viewPager;
    ForecastViewAdapter forecastViewAdapter;

    public static Intent newIntent(Context context, String id){
        Intent intent = new Intent(context, ViewForecastActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forecast);

        ForecastManager forecastManager = ForecastManager.getInstance(this);
        mForecast = forecastManager.getCurrentForecast();


        viewPager = findViewById(R.id.forecastViewPager);
        tabLayout = findViewById(R.id.forecastTabLayout);


        for(ForecastResult result: mForecast.getForecastResults()) {
            tabLayout.addTab(tabLayout.newTab());
        }


        forecastViewAdapter = new ForecastViewAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(forecastViewAdapter);

        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager);


        double[] primitiveArray = mForecast.getForecastInputs().get(0).getSeries();
        int len = primitiveArray.length;
        Double[] input = new Double[len];
        for (int index = len-1; index >= 0; index--)
            input[index] = Double.valueOf(primitiveArray[index]);
        drawInput(input);

        primitiveArray = mForecast.getForecastResults().get(0).getSeries();
        len = primitiveArray.length;
        Double[] results = new Double[len];
        for (int index = 0; index < len; index++)
            input[index] = Double.valueOf(primitiveArray[index]);
        //drawResults(results);
    }

    private void drawInput(Double[] input){
        ListView listView = (ListView) findViewById(R.id.forecast_input);
        ArrayAdapter<Double> doubleAdapter = new ArrayAdapter<Double>(this, R.layout.activity_view_forecast_list, input);
        listView.setAdapter(doubleAdapter);
    }

    private void drawResults(Double[] results){
        ListView listView = (ListView) findViewById(R.id.forecast_results);
        ArrayAdapter<Double> doubleAdapter = new ArrayAdapter<Double>(this, R.layout.activity_view_forecast_list, results);
        listView.setAdapter(doubleAdapter);
    }
}