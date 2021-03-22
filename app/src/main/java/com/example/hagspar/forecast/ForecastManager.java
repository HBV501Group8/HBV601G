package com.example.hagspar.forecast;

import android.content.Context;
import android.util.Log;
import com.example.hagspar.forecast.data.Forecast;
import com.example.hagspar.networking.NetworkCallback;
import com.example.hagspar.networking.NetworkManager;

import java.util.ArrayList;

public class ForecastManager {

    private static ForecastManager mInstance;
    private Context mContext;
    private Forecast mCurrentForecast;
    private ArrayList<String[]> mForecastList = new ArrayList<String[]>();

    public static synchronized ForecastManager getInstance(Context context){
        if(mInstance == null) {
            mInstance = new ForecastManager(context);
        }
        return mInstance;
    }

    private ForecastManager(Context context) {
        mContext = context;
    }

    public void viewForecast(String id){

    }

    public void newForecast(){
        // TODO
    }

    public void deleteForecast(String id){
        //TODO
    }

    public void updateForecast(String id){
        //TODO
    }


}
