package com.example.logintest3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    EditText username, password, repassword,realname,email;
    Button signup, signin, btnreset,btnAdmin,btnHome,btnCreateUser;
    DBHelper DB,DB2;

    // Main activty heldur utan um aðalvalmynd sem og
    // registera nýjan notanda
    // Eins og stendur er þetta í debug mode þar semég get auðvedlega
    // nálgast það sem ég er að debugga



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //List<String> result = Files.readAllLines()

        // Tilvísun í breytur

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        realname = (EditText) findViewById(R.id.userRealname);
        email = (EditText) findViewById(R.id.emailuser);

        repassword = (EditText) findViewById(R.id.repassword);
        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);
        btnAdmin = (Button) findViewById(R.id.btnadmin);
        btnHome = (Button) findViewById(R.id.btnhome);
        btnCreateUser = (Button)  findViewById(R.id.btnInsertUser);

        List<String> returnList = new ArrayList<>();

        DB = new DBHelper(this);

        // Event signup
        // Þetta event höndlar nýskráningu notanda
        // TODO: Færa í sér activity

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Náí öll innslegin eigindi

                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String mail = email.getText().toString();
                String realName = realname.getText().toString();

                // Athuga hvort slegin sé inn í öll svæði

                if(user.equals("")||pass.equals("")||repass.equals("") || mail.equals(""))
                    Toast.makeText(MainActivity.this, R.string.Enter_All_Fields, Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        // Notandi er nýskráður í users

                        Boolean checkuser = DB.checkusername(user);

                        // Ef notandi er ekki þegar til

                        if(checkuser==false){
                            Boolean insert = DB.insertData(user, pass,mail,realName);
                            if(insert==true){
                                Toast.makeText(MainActivity.this, R.string.Register_Success, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),adminActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, R.string.Registration_Failed, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, R.string.User_Exssits, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, R.string.Password_NotMatch, Toast.LENGTH_SHORT).show();
                    }
                } }
        });

        // Event kalla á login form

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // Event kalla á admin stýringu DEBUG

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), adminActivity.class);
                startActivity(intent);

            }
        });

        // EVent fyrir shortcut inn a heimaíðu notanda DEBUG

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                startActivity(intent);

            }
        });

    }



}
