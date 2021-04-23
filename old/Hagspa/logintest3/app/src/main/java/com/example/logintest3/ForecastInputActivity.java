package com.example.logintest3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ForecastInputActivity extends AppCompatActivity {
    Button btnLogout,btnSubmit;
    DBHelper DB;
    ListView llAll;
    EditText forecastNameholder,forecastDuration;
    RadioButton radnar,radarima;
    ArrayList<String> SelectedItems = new ArrayList<>();
    public List<String> returnList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_input);


        // Finna breytur í view
        radnar = (RadioButton) findViewById(R.id.radVAR);
        radarima = (RadioButton) findViewById(R.id.radArima);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        llAll = (ListView) findViewById(R.id.ll_ViewAll);
        DB = new DBHelper(this);
        forecastNameholder = (EditText) findViewById(R.id.forecastName);
        forecastDuration = (EditText) findViewById(R.id.forecastDuration);


        // Statísk gildi fyrir forecast attributes

        returnList.add(getString(R.string.Fjoldi_Islenskra));
        returnList.add(getString(R.string.Fjoldi_Erlendra));
        returnList.add(getString(R.string.Atvinnuleysi));
        returnList.add(getString(R.string.Atvinnuleysi_RVK));
        returnList.add(getString(R.string.Einkaneysla));
        returnList.add(getString(R.string.Samneysla));
        returnList.add(getString(R.string.Fjarmunamyndun));
        returnList.add(getString(R.string.Utflutningurvara));
        returnList.add(getString(R.string.Utflutningur_Thjonustu));
        returnList.add(getString(R.string.Innflutningur_thjonustu));
        returnList.add(getString(R.string.Verg_landframleidsla));

        // Setja upp adapter fyrir checkbox

        llAll.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter userArrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_multiple_choice,returnList);
        llAll.setAdapter(userArrayAdapter);

        llAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            // Event handler fyrir lista af users
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //    Intent intent  = new Intent(getApplicationContext(), UserDetailActivity.class);

                String Value = parent.getItemAtPosition(position).toString();
                if(!SelectedItems.contains(Value)) {
                    SelectedItems.add(Value + "\n");
                    Value = position+"";
                }
                Toast.makeText(ForecastInputActivity.this,Value.toString(),Toast.LENGTH_SHORT).show();
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

        // Event handler fyrir Submit sem skilar viðföngum spáar
        // áfram

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ná í gildi

                String fname = forecastNameholder.getText().toString();
                String fDuration = forecastDuration.getText().toString();
                // Validera input nafn á spá
                if(fname.equals("")) {
                    Toast.makeText(ForecastInputActivity.this, R.string.Need_Forecast_name, Toast.LENGTH_SHORT).show();
                    return;
                }
                //Validera input tímalengd spáár
                int duration = Integer.parseInt(fDuration);
                // Hámark spátíma er 24 mánuðir
                if(duration > 24) {
                    Toast.makeText(ForecastInputActivity.this, R.string.Too_Large_Duration, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fDuration.equals("")) {
                    Toast.makeText(ForecastInputActivity.this, R.string.Enter_Forecast_Duration, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Senda viðföng áram í næsta activity
                Intent intent  = new Intent(getApplicationContext(),ForecastActivity.class);
                if(radnar.isSelected()){
                    intent.putExtra(getString(R.string.forecastType), getString(R.string.NAR));
                }
                else if(radarima.isSelected()){
                    intent.putExtra("forecastType", getString(R.string.Arima));
                }

                if(SelectedItems.isEmpty()) {
                    Toast.makeText(ForecastInputActivity.this, R.string.Attrbutes_selected, Toast.LENGTH_SHORT).show();
                    return;
                }

                intent.putExtra(getString(R.string.selectedItems), SelectedItems);
                intent.putExtra(getString(R.string.forecastName), fname);
                intent.putExtra(getString(R.string.forecastDuration), fDuration);
                startActivity(intent);
            }


        });
    }

}