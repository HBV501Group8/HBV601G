package com.example.logintest3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity {
    EditText username, password, repassword, realname, email;
    DBHelper DB;
    Button btnUpdate,btnDelete;
    List<String> list = new ArrayList<>();

    // Userdetail activity
    // Heldur utan um eigindi og breytngar
    // notanda þar sem hægt er að uppfæra upplýsingar
    // notanda


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = new DBHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        // Tivlsínar í breytur

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        realname = (EditText) findViewById(R.id.userRealname);
        email = (EditText) findViewById(R.id.emailuser);
        btnUpdate = (Button) findViewById(R.id.btnupdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // Ná í gögn frá parent formi

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            // Ná í eignidi notanda úr user  töflu

            String userval = extras.getString(getString(R.string.User_Key_Admin));

            // Setja eigindi í form

            list = DB.getUser((userval));
            username.setText(list.get(3));
            password.setText(list.get(4));
            email.setText(list.get(2));
            realname.setText(list.get(1));

          }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName = username.getText().toString();
                String strPassword = password.getText().toString();
                String strEmail = email.getText().toString();
                String strName = realname.getText().toString();
                boolean success = DB.updateUser(strUserName,strPassword,strEmail,strName);
                Intent intent  = new Intent(getApplicationContext(), adminActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String struserName = list.get(3);
                boolean success =  DB.deleteUser(struserName);

                Intent intent  = new Intent(getApplicationContext(), adminActivity.class);
                startActivity(intent);
            }
        });
    }
}
