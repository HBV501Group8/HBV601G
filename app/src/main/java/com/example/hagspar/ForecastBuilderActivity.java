package com.example.hagspar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hagspar.forecast.ForecastManager;
import com.example.hagspar.networking.NetworkCallback;
import com.example.hagspar.networking.NetworkManager;

import java.util.ArrayList;

public class ForecastBuilderActivity extends AppCompatActivity {

    private String ERROR_MESSAGE_NAME = "Vantar nafn á spá!";
    private String ERROR_MESSAGE_SERIES = "Engar spábreytur valdar!";

    public static Intent newIntent(Context context, String username){
        Intent intent = new Intent(context, ForecastBuilderActivity.class);
        intent.putExtra("username", username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_builder);

        // Define options for time_picker spinner
        Spinner spinner = (Spinner) findViewById(R.id.length_picker);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_picker_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Add event handler for generate Forecast button
        Button generateBtn = (Button) findViewById(R.id.generate_forecast_button);
        generateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Define variables to be sent for generation
                String name;
                String length;
                String forecastModel;
                ArrayList<String> seriesNames = new ArrayList<String>();


                // Define inputs
                EditText nameEdTxt = (EditText) findViewById(R.id.forcast_name_input);
                Spinner lengthSpinner = (Spinner) findViewById(R.id.length_picker);

                RadioButton arimaRadio = (RadioButton) findViewById(R.id.arima_radio_button);

                ArrayList<CheckBox> series = new ArrayList<>();
                series.add((CheckBox) findViewById(R.id.mannfjoldi_is_checkbox));
                series.add((CheckBox) findViewById(R.id.mannfjoldi_erl_checkbox));
                series.add((CheckBox) findViewById(R.id.atvinnuleysi_rvk_checkbox));
                series.add((CheckBox) findViewById(R.id.atvinnuleysi_lands_checkbox));
                series.add((CheckBox) findViewById(R.id.einkaneysla_checkbox));
                series.add((CheckBox) findViewById(R.id.samneysla_checkbox));
                series.add((CheckBox) findViewById(R.id.fjarmunamyndun_checkbox));
                series.add((CheckBox) findViewById(R.id.vara_ut_checkbox));
                series.add((CheckBox) findViewById(R.id.vara_inn_checkbox));
                series.add((CheckBox) findViewById(R.id.thjonusta_ut_checkbox));
                series.add((CheckBox) findViewById(R.id.thjonusta_inn_checkbox));
                series.add((CheckBox) findViewById(R.id.vlf_checkbox));

                // Get and validate inputs
                if(nameEdTxt.getText() == null) {
                    Toast.makeText(ForecastBuilderActivity.this, ERROR_MESSAGE_NAME, Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    name = nameEdTxt.getText().toString();
                }

                length = lengthSpinner.getSelectedItem().toString();

                if(arimaRadio.isChecked()) {
                    forecastModel = "arima";
                }else {
                    forecastModel = "var";
                }


                series.forEach((checkbox) -> {
                    if(checkbox.isChecked()) {
                        seriesNames.add(checkbox.getText().toString());
                    }
                });

                if(series.isEmpty()) {
                    Toast.makeText(ForecastBuilderActivity.this, ERROR_MESSAGE_SERIES, Toast.LENGTH_SHORT).show();
                }

                // Call ForecastManager class to generate forecast and move to forecast view if
                // successfully generated
                ForecastManager forecastManager = ForecastManager.getInstance(ForecastBuilderActivity.this);
                forecastManager.generateForecast(getIntent().getStringExtra("username"), name, length, forecastModel,
                                                 seriesNames, ForecastBuilderActivity.this);
            }
        });
    }
}