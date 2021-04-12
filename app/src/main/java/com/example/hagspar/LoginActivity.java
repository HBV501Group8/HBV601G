package com.example.hagspar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hagspar.networking.NetworkCallback;
import com.example.hagspar.networking.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private String LOGIN_SUCCESSFUL = "Innskráning mótekin";
    private String ERROR_MESSAGE_LOGIN = "Innskráning mistókst, reyndu aftur";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button submitBtn = (Button) findViewById(R.id.login_button);

        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText usernameField = (EditText) findViewById(R.id.username_input);
                EditText passwordField = (EditText) findViewById(R.id.password_input);

                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();


                NetworkManager networkManager = NetworkManager.getInstance(LoginActivity.this);
                networkManager.userSignIn(username, password, new NetworkCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(result.equals("success")) {
                            Toast.makeText(LoginActivity.this, LOGIN_SUCCESSFUL, Toast.LENGTH_SHORT).show();
                            startActivity(ForecastManagerActivity.newIntent(LoginActivity.this, username));
                        }else{
                            Toast.makeText(LoginActivity.this, ERROR_MESSAGE_LOGIN, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail(String errorString) {
                        Toast.makeText(LoginActivity.this, ERROR_MESSAGE_LOGIN, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}