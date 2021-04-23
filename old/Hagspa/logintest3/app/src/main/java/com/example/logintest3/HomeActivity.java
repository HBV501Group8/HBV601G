package com.example.logintest3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import Model.Users;

public class HomeActivity extends AppCompatActivity {
    Button btnLogout,btncreate;
    ListView llAll;
    DBHelper DB;
    @Override

    // Home activity heldur utan um heimasvæði notanda þar sem hann
    // getur búið til eða náð í gamlar spár og einnige eytt

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DB = new DBHelper(this);

        // Tilvísun í breytur
        btncreate = (Button) findViewById(R.id.brncreate);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        llAll = (ListView) findViewById(R.id.ll_ViewAll);

        // Event handler fyrir logout

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), ForecastInputActivity.class);
                startActivity(intent);            }
        });
    }

}