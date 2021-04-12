package com.example.hagspar.forecast;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.hagspar.ForecastBuilderActivity;
import com.example.hagspar.ForecastManagerActivity;
import com.example.hagspar.LoginActivity;
import com.example.hagspar.ViewForecastActivity;
import com.example.hagspar.forecast.data.Forecast;
import com.example.hagspar.forecast.data.ForecastInput;
import com.example.hagspar.forecast.data.ForecastResult;
import com.example.hagspar.networking.NetworkCallback;
import com.example.hagspar.networking.NetworkManager;
import com.example.hagspar.usermanagement.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ForecastManager {

    private static ForecastManager mInstance;
    private Context mContext;
    private Forecast mCurrentForecast;

    private String ERROR_MESSAGE_GENERATION = "Ekki tókst að smíða spá, reyndu aftur!";

    public static synchronized ForecastManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ForecastManager(context);
        }
        return mInstance;
    }

    private ForecastManager(Context context) {
        mContext = context;
    }

    public void viewForecast(String id, Context context) {
        NetworkManager networkManager = NetworkManager.getInstance(context);
        networkManager.getForecast(id, new NetworkCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonBody = new JSONObject(result);
                    String id = jsonBody.getString("id");
                    String name = jsonBody.getString("forecastName");
                    String time = jsonBody.getString("generatedTime");
                    String input_str = jsonBody.getJSONArray("forecastInputs").toString();
                    String results_str = jsonBody.getJSONArray("forecastResults").toString();

                    // Parse input and results into objects
                    Gson gson = new Gson();
                    // input_str into list of ForecastInput
                    Type listType =  new TypeToken<List<ForecastInput>>(){}.getType();
                    List<ForecastInput> input = gson.fromJson(input_str, listType);
                    // results_str into list of ForecastResult
                    listType =  new TypeToken<List<ForecastResult>>(){}.getType();
                    List<ForecastResult> results = gson.fromJson(results_str, listType);

                    // Construct Forecast object and assign as current forecast
                    setCurrentForecast(new Forecast(id, name, time, results, input));

                    // Start view forecast activity
                    context.startActivity(ViewForecastActivity.newIntent(context, id));


                } catch (JSONException e) {
                    Log.e("test", e.toString());
                }
            }
            @Override
            public void onFail(String errorString) {
                Log.e("test", errorString);
            }
        });
    }

    public void generateForecast(String username, String name, String length, String forecastModel,
                                 ArrayList<String> seriesNames, Context context) {
        NetworkManager networkManager = NetworkManager.getInstance(context);
        networkManager.generateForecast(username, name, length, forecastModel, seriesNames, new NetworkCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result.equals("failed")) {
                    Toast.makeText(context, ERROR_MESSAGE_GENERATION, Toast.LENGTH_SHORT).show();
                    Log.e("test", result);
                }else{
                    Log.e("test", result);
                    viewForecast(result, context);
                }
            }
            @Override
            public void onFail(String errorString) {
                Toast.makeText(context, ERROR_MESSAGE_GENERATION, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void deleteForecast(String id) {
        //TODO
        // þetta er bara kall á get method á server! Þarf ekki meira!
    }

    public void updateForecast(String id) {
        //TODO
        // Þetta er bara kall á update foreast method á server og
        // svo kallað a´view foreast þegar búið er að genereita nýtt!
        // Einfaldara generate function!
    }


    public void getForecastList(String username, Context context, ForecastCallback<ArrayList<String[]>> callback) {
        NetworkManager networkManager = NetworkManager.getInstance(context);
        networkManager.getForecastList(username, new NetworkCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<String[]>>() {
                }.getType();
                Log.e("test", "Gson");
                callback.whenReady(gson.fromJson(result, listType));
            }

            @Override
            public void onFail(String errorString) {
                Log.e("test", errorString);
                //TODO Handle error
            }
        });
    }

    public Forecast getCurrentForecast() {
        return mCurrentForecast;
    }

    public void setCurrentForecast(Forecast currentForecast) {
        this.mCurrentForecast = currentForecast;
    }
}