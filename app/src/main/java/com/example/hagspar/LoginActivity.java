package com.example.hagspar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hagspar.adapters_utils.LoadingUtil;
import com.example.hagspar.networking.NetworkCallback;
import com.example.hagspar.networking.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {



    private final String LOGIN_SUCCESSFUL = "Innskráning mótekin";
    private final String ERROR_MESSAGE_LOGIN = "Innskráning mistókst, reyndu aftur";

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View loadingOverlay = (View) findViewById(R.id.loading_overlay);
        loadingOverlay.bringToFront();

        Button submitBtn = (Button) findViewById(R.id.login_button);
        Button registerBtn = (Button) findViewById(R.id.register_button);

        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText usernameField = (EditText) findViewById(R.id.username_input);
                EditText passwordField = (EditText) findViewById(R.id.password_input);

                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                LoadingUtil.animateView(loadingOverlay, View.VISIBLE, 0.4f, 200);

                NetworkManager networkManager = NetworkManager.getInstance(LoginActivity.this);
                networkManager.userSignIn(username, password, new NetworkCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(result.equals("success")) {
                            LoadingUtil.animateView(loadingOverlay, View.GONE, 0, 200);
                            Toast.makeText(LoginActivity.this, LOGIN_SUCCESSFUL, Toast.LENGTH_SHORT).show();
                            startActivity(ForecastManagerActivity.newIntent(LoginActivity.this, username));
                        }else{
                            LoadingUtil.animateView(loadingOverlay, View.GONE, 0, 200);
                            Toast.makeText(LoginActivity.this, ERROR_MESSAGE_LOGIN, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail(String errorString) {
                        LoadingUtil.animateView(loadingOverlay, View.GONE, 0, 200);
                        Toast.makeText(LoginActivity.this, ERROR_MESSAGE_LOGIN, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterActivity.newIntent(LoginActivity.this));
            }
        });
    }
}