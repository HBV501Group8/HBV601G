package com.example.hagspar.adapters_utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hagspar.R;
import com.example.hagspar.forecast.ForecastCallback;
import com.example.hagspar.forecast.ForecastManager;

import java.util.ArrayList;

public class ForecastListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String[]> list = new ArrayList<String[]>();
    private Context context;
    private Intent intent;

    public ForecastListAdapter(ArrayList<String[]> list, Context context, Intent intent) {
        this.list = list;
        this.context = context;
        this.intent = intent;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_forecast_manager_forecast_list, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position)[1]);
        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button) view.findViewById(R.id.delete_btn);
        Button viewBtn = (Button) view.findViewById(R.id.view_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("test", "delete takki");
                ForecastManager.getInstance(context).deleteForecast(list.get(position)[0], context.getApplicationContext(),
                        new ForecastCallback<String>() {
                            @Override
                            public void whenReady(String ready) {
                                if(ready.equals("success")) {
                                    list.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context.getApplicationContext(), "Spá hefur verið eytt", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context.getApplicationContext(), "Ekki tókst að eyða spá", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            }
        });
        viewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("test", "view takki");
                ForecastManager forecastManager = ForecastManager.getInstance(context);
                forecastManager.viewForecast(list.get(position)[0], intent.getStringExtra("username"), context);
            }
        });

        return view;
    }
}
