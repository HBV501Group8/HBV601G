package com.example.hagspar.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hagspar.forecast.data.Forecast;
import com.example.hagspar.forecast.data.ForecastInput;
import com.example.hagspar.forecast.data.ForecastResult;
import com.example.hagspar.usermanagement.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
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

    public void getForecastList(String username, final NetworkCallback<String> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, SERVER_URL + "listforecasts/" + username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
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

    public void getForecast(String id,  final NetworkCallback<String> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, SERVER_URL + "getforecast/" + id , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
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


    public void generateForecast(String username, String name, String length, String forecastModel,
                                 ArrayList<String> seriesNames, final NetworkCallback<String> callback){
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username);
            postData.put("name", name);
            postData.put("length", length);
            postData.put("forecastModel", forecastModel);
            postData.put("seriesNames", new JSONArray(seriesNames));
        } catch(JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, SERVER_URL + "/forecastgeneration", postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(response.get("generatedID").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.toString());
            }
        }
        );
        // Does not retry and will wait a full minute before timeout
        request.setRetryPolicy(new DefaultRetryPolicy(60000, 0, 0));
        mQueue.add(request);
    }

    public void deleteForecast(String id,  final NetworkCallback<String> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, SERVER_URL + "deleteforecast/" + id , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
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

    public void updateForecast(String id,  final NetworkCallback<String> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, SERVER_URL + "updateforecast/" + id , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("test", response.toString());
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.toString());
            }
        }
        );
        // Does not retry and will wait a full minute before timeout
        request.setRetryPolicy(new DefaultRetryPolicy(60000, 0, 0));
        mQueue.add(request);
    }

    public void userSignIn(String username, String password, final NetworkCallback<String> callback) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username);
            postData.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, SERVER_URL + "loginsubmit/", postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(response.get("login").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void userRegister(String name, String email, String username, String password,
                             final NetworkCallback<String> callback) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("name", name);
            postData.put("email", email);
            postData.put("username", username);
            postData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, SERVER_URL + "registeruser/", postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(response.get("register").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
