package com.example.hagspar.forecast;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.hagspar.ForecastManagerActivity;
import com.example.hagspar.R;
import com.example.hagspar.ViewForecastActivity;

import java.util.ArrayList;

public class ForecastListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String[]> list = new ArrayList<String[]>();
    private Context context;

    public ForecastListAdapter(ArrayList<String[]> list, Context context) {
        this.list = list;
        this.context = context;
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
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position)[1]);
        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button addBtn = (Button)view.findViewById(R.id.view_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("test", "delete takki");
                //ForecastManager.getInstance(context).deleteForecast(list.get(position)[0]);

                list.remove(position);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Log.e("test", "view takki");
                //ForecastManager.getInstance(context).viewForecast(list.get(position)[0]);
                context.startActivity(ViewForecastActivity.newIntent(context,list.get(position)[0]));
            }
        });

        return view;
    }
}
