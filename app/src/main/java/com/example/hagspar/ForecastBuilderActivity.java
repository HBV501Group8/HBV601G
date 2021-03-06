package com.example.hagspar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hagspar.adapters_utils.LoadingUtil;
import com.example.hagspar.forecast.ForecastCallback;
import com.example.hagspar.forecast.ForecastManager;
import com.example.hagspar.networking.NetworkCallback;
import com.example.hagspar.networking.NetworkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForecastBuilderActivity extends AppCompatActivity {

    private final String ERROR_MESSAGE_NAME = "Vantar nafn á spá!";
    private final String ERROR_MESSAGE_SERIES = "Engar spábreytur valdar!";
    private final String ERROR_MESSAGE_GENERATION = "Ekki tókst að smíða spá, reyndu aftur!";

    private static final Map<String, String> seriesNameLookup = new HashMap<String, String>();
    {
        seriesNameLookup.put("Fjöldi íslenskra ríkisborgara","Mannfjoldi_is");
        seriesNameLookup.put("Fjöldi erlendra ríkisborgara","Mannfjoldi_erl");
        seriesNameLookup.put("Atvinnuleysi í Reykjavík", "Atvinnul_rvk");
        seriesNameLookup.put("Atvinnuleysi", "Atvinnul_land");
        seriesNameLookup.put("Einkaneysla","Einkaneysla");
        seriesNameLookup.put("Samneysla","Samneysla");
        seriesNameLookup.put("Fjármunamyndun","Fjarmunamyndun");
        seriesNameLookup.put("Útflutningur vara", "Vara_ut");
        seriesNameLookup.put("Innflutningur vara", "Vara_inn");
        seriesNameLookup.put("Útflutningur þjónustu", "Thjonusta_ut");
        seriesNameLookup.put("Innflutningur þjónustu","Thjonusta_inn");
        seriesNameLookup.put("Verg landsframleiðsla","VLF");
    }

    public static Intent newIntent(Context context, String username){
        Intent intent = new Intent(context, ForecastBuilderActivity.class);
        intent.putExtra("username", username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_builder);

        // Loading overlay
        View loadingOverlay = (View) findViewById(R.id.loading_overlay);
        loadingOverlay.bringToFront();

        // Define options for time_picker spinner
        Spinner spinner = (Spinner) findViewById(R.id.length_picker);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_picker_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        signOut();
        notandiText();

        // Add event handler for generate Forecast button
        Button generateBtn = (Button) findViewById(R.id.generate_forecast_button);
        generateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // Activate loading overlay
                LoadingUtil.animateView(loadingOverlay, View.VISIBLE, 0.4f, 200);




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
                    LoadingUtil.animateView(loadingOverlay, View.GONE, 0, 200);
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
                        seriesNames.add(seriesNameLookup.get(checkbox.getText().toString()));
                    }
                });

                if(series.isEmpty()) {
                    LoadingUtil.animateView(loadingOverlay, View.GONE, 0, 200);
                    Toast.makeText(ForecastBuilderActivity.this, ERROR_MESSAGE_SERIES, Toast.LENGTH_SHORT).show();
                    return;
                }

                generateBtn.setEnabled(false);

                // Call ForecastManager class to generate forecast and move to forecast view if
                // successfully generated
                ForecastManager forecastManager = ForecastManager.getInstance(ForecastBuilderActivity.this);
                forecastManager.generateForecast(getIntent().getStringExtra("username"), name, length, forecastModel,
                        seriesNames, ForecastBuilderActivity.this, new ForecastCallback<String>() {
                            @Override
                            public void whenReady(String ready) {
                                if(ready.equals("failed")) {
                                    Log.e("test", ready);
                                    generateBtn.setEnabled(true);
                                    LoadingUtil.animateView(loadingOverlay, View.GONE, 0, 200);
                                    Toast.makeText(ForecastBuilderActivity.this, ERROR_MESSAGE_GENERATION, Toast.LENGTH_SHORT).show();
                                }else {
                                    forecastManager.viewForecast(ready, getIntent().getStringExtra("username"), ForecastBuilderActivity.this);
                                }
                            }
                        });
            }
        });
    }

    private void notandiText() {

        TextView notandiText = (TextView) findViewById(R.id.notandi_text_builder);

        //JSONObject postData = new JSONObject();
        String userName = getIntent().getStringExtra("username");
        notandiText.setText(userName);
    }

    private void signOut() {
        //Sign out button
        Button logoutButton = (Button) findViewById(R.id.log_out_builder);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();
                startActivity(new Intent(ForecastBuilderActivity.this, LoginActivity.class));
            }
        });

    }
}