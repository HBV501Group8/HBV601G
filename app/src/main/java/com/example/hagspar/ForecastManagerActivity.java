package com.example.hagspar;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.hagspar.adapters_utils.LoadingUtil;
import com.example.hagspar.forecast.ForecastCallback;
import com.example.hagspar.adapters_utils.ForecastListAdapter;
import com.example.hagspar.forecast.ForecastManager;
import com.example.hagspar.usermanagement.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class ForecastManagerActivity extends AppCompatActivity {

    private View loadingOverlay;
    private ListView listView;

    public static Intent newIntent(Context context, String username){
        Intent intent = new Intent(context, ForecastManagerActivity.class);
        intent.putExtra("username", username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_manager);

        loadingOverlay = (View) findViewById(R.id.loading_overlay);
        loadingOverlay.bringToFront();
        listView = (ListView)findViewById(R.id.forecastlist);
        listView.setVisibility(View.GONE);

        LoadingUtil.animateView(loadingOverlay, View.VISIBLE, 0.4f, 200);

        Button genForecastBtn = (Button) findViewById(R.id.new_forecast_btn);
        genForecastBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(ForecastBuilderActivity.newIntent(ForecastManagerActivity.this, getIntent().getStringExtra("username")));
            }
        });

        logoutButton();
        userText();
    }

    private void userText() {

        //TODO Eftir að tengja við userJ
        String SERVER_URL = "http://10.0.2.2:8080/android/";
        TextView notandiText = (TextView) findViewById(R.id.notandi_text);

        JSONObject postData = new JSONObject();

        try {
            //virkar ekki
            String username = postData.getString("name");  //eða username
            notandiText.setText(username);
        } catch (JSONException e) {
            e.printStackTrace();
            //sjá hvort að textview er á réttum stað
            notandiText.setText("Tókst ekki");
        }





    }

    private void logoutButton() {
        //sign out button
        Button logoutButton = (Button) findViewById(R.id.log_out);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                startActivity(new Intent(ForecastManagerActivity.this, LoginActivity.class));
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
                        LoadingUtil.animateView(loadingOverlay, View.GONE, 0, 200);
                        drawList(ready);
                    }
                });
    }

    public void drawList (ArrayList<String[]> list){
        ForecastListAdapter forecastListAdapter = new ForecastListAdapter(list, this, getIntent(), loadingOverlay, listView);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(forecastListAdapter);
    }
}