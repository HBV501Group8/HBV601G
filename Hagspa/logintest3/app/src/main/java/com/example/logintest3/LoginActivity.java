package com.example.logintest3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, password,email,realName;
    Button btnlogin;
    DBHelper DB;

    // Login activty heldur utan um login fyrir user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ná í tilvísanir á breytur

        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        DB = new DBHelper(this);

        // Event handler fyrir login smell

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Ná í input svæði til að testa

                String user = username.getText().toString();
                String pass = password.getText().toString();

                // Ef vantar texta í svæði þá láta motnada vita

                if(user.equals("")||pass.equals("") )
                    Toast.makeText(LoginActivity.this, R.string.Enter_All_Fields_Login, Toast.LENGTH_SHORT).show();
                else{

                    // Annars validera notanda

                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        // Fara á heimavæði
                        Toast.makeText(LoginActivity.this, R.string.Sign_In_Successful, Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else{
                        // validering tekst ekki
                        Toast.makeText(LoginActivity.this, R.string.Invalid_credentials, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}