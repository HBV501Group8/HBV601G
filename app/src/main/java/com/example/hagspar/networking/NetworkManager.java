package com.example.hagspar.networking;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hagspar.forecast.data.Forecast;
import com.example.hagspar.forecast.data.ForecastInput;
import com.example.hagspar.forecast.data.ForecastResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NetworkManager {

    private static final String SERVER_URL = "http://10.0.2.2:8080/android/";
    // Þegar sími er tengdur með usb
    //private static final String SERVER_URL = "http://localhost:8080/android/";

    private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private Context mContext;

    public static NetworkManager getInstance(Context context){
        if(mInstance == null) {
            mInstance = new NetworkManager(context.getApplicationContext());
        }
        return mInstance;
    }


    private NetworkManager(Context context) {
        mContext = context;
        mQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(mQueue == null){
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    public void getForecastList(final NetworkCallback<ArrayList<String[]>> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, SERVER_URL + "listforecasts", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType =  new TypeToken<ArrayList<String[]>>(){}.getType();
                Log.e("test", "Gson");
                ArrayList<String[]> forecastList = gson.fromJson(response, listType);
                callback.onSuccess(forecastList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.toString());
            }
        }
        );
        mQueue.add(request);
    }

    public void getForecast(String id,  final NetworkCallback<Forecast> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, SERVER_URL + "getforecast/" + id , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonBody = new JSONObject(response);
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

                    // Construct Forecast object
                    Forecast forecast = new Forecast(id, name, time, results, input);
                    callback.onSuccess(forecast);

                } catch (JSONException e) {
                    Log.e("test", e.toString());
                }


                //Gson gson = new Gson();
                //String str_obj = gson.toJson(response);
                //Forecast forecast = gson.fromJson(response, Forecast.class);
                //callback.onSuccess(forecast);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.toString());
            }
        }
        );
        mQueue.add(request);
    }
}
