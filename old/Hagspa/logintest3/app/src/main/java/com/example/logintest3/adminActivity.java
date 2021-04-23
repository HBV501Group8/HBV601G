package com.example.logintest3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.Users;

public class adminActivity extends AppCompatActivity {
    Button btnViewAll,btnLogout;
    ListView llAll;
    DBHelper DB;
    List<String> returnList = new ArrayList<>();
    @Override


    // Höndlar Admin activity sem er utanhald um
    // notendur og birtir lista af öllum notendum sem síðan
    // er hægt að smella á tila ðs koða user


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Referenca breytur

        btnLogout = (Button) findViewById(R.id.btnLogout);
        llAll = (ListView) findViewById(R.id.ll_ViewAll);
        DB = new DBHelper(this);
        returnList = DB.getAll();
        ArrayAdapter userArrayAdapter = new ArrayAdapter<String>(this,  R.layout.custom,R.id.textView,returnList);
        llAll.setAdapter(userArrayAdapter);
        llAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       @Override

           // Event handler fyrir lista af users

          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent  = new Intent(getApplicationContext(), UserDetailActivity.class);
              String Value = returnList.get(position);
              intent.putExtra(getString(R.string.User_Key_Admin), Value);
                          startActivity(intent);
                     }
                });

            // Event handler fyrir logout

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }


            });
        }


    }

