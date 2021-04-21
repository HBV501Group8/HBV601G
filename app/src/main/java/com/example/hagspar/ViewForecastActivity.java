package com.example.hagspar;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hagspar.forecast.ForecastManager;
import com.example.hagspar.adapters_utils.ForecastViewAdapter;
import com.example.hagspar.forecast.data.Forecast;
import com.example.hagspar.forecast.data.ForecastResult;
import com.google.android.material.tabs.TabLayout;

public class ViewForecastActivity extends AppCompatActivity {

    private static Forecast mForecast;

    TabLayout tabLayout;
    ViewPager viewPager;
    ForecastViewAdapter forecastViewAdapter;

    public static Intent newIntent(Context context, String id, String username){
        Intent intent = new Intent(context, ViewForecastActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("username", username);
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

        signOut();

        for(ForecastResult result: mForecast.getForecastResults()) {
            tabLayout.addTab(tabLayout.newTab());
        }


        forecastViewAdapter = new ForecastViewAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(forecastViewAdapter);

        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager);
    }

    private void signOut() {
        Button signOutButton = (Button)findViewById(R.id.log_out_view);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish()
                startActivity(new Intent(ViewForecastActivity.this, LoginActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(ForecastManagerActivity.newIntent(this, getIntent().getStringExtra("username")));
    }
}