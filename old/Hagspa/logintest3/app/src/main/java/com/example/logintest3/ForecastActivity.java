package com.example.logintest3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {
    Button btnLogout;
    TextView txtview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        txtview  = (TextView) findViewById(R.id.txtviewar);

        //Nær í valin eigindi spáar

        ArrayList<String> numbersList = (ArrayList<String>) getIntent().getSerializableExtra(getString(R.string.selectedItems));
        txtview.setText(String.valueOf(numbersList));

        btnLogout= (Button) findViewById(R.id.btnLogouthandler);

        // Logout

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}