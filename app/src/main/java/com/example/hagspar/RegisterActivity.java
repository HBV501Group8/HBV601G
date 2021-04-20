package com.example.hagspar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hagspar.adapters_utils.LoadingUtil;
import com.example.hagspar.networking.NetworkCallback;
import com.example.hagspar.networking.NetworkManager;

public class RegisterActivity extends AppCompatActivity {


    private final String REGISTER_SUCCESS = "Notandi skráður";
    private final String ERROR_MESSAGE_EXISTS = "Nýskráning mistókst, notandanafn þegar til";
    private final String ERROR_MESSAGE_FAILED_REQUEST = "Ekki náðist samband við vefþjón";

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        View loadingOverlay = (View) findViewById(R.id.loading_overlay);
        loadingOverlay.bringToFront();

        Button submitBtn = (Button) findViewById(R.id.register_new_user_button);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameField = (EditText) findViewById(R.id.name_register_input);
                EditText emailField = (EditText) findViewById(R.id.email_register_input);
                EditText usernameField = (EditText) findViewById(R.id.username_register_input);
                EditText passwordField = (EditText) findViewById(R.id.password_register_input);

                String name = nameField.getText().toString();
                String email = emailField.getText().toString();
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                LoadingUtil.animateView(loadingOverlay, View.VISIBLE, 0.4f, 200);

                NetworkManager networkManager = NetworkManager.getInstance(RegisterActivity.this);
                networkManager.userRegister(name, email, username, password, new NetworkCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(result.equals("success")) {
                            LoadingUtil.animateView(loadingOverlay, View.GONE, 0, 200);
                            Toast.makeText(RegisterActivity.this, REGISTER_SUCCESS, Toast.LENGTH_SHORT).show();
                            startActivity(LoginActivity.newIntent(RegisterActivity.this));
                        }else{
                            LoadingUtil.animateView(loadingOverlay, View.GONE, 0, 200);
                            Toast.makeText(RegisterActivity.this, ERROR_MESSAGE_EXISTS, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail(String errorString) {
                        LoadingUtil.animateView(loadingOverlay, View.GONE, 0, 200);
                        Toast.makeText(RegisterActivity.this, ERROR_MESSAGE_FAILED_REQUEST, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}