package com.example.hagspar;

import android.graphics.Color;
import android.icu.text.CaseMap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hagspar.forecast.ForecastManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewForecastFragment extends Fragment {

    private static final String ARG_PARAM = "position";
    private int mPosition;
    private ForecastManager forecastManager;

    private final String RESULTS_LABEL = "Spá";
    private final String UPPER_LABEL = "Efri mörk";
    private final String LOWER_LABEL = "Neðri mörk";

    private final float LINE_WIDTH = 3f;

    public ViewForecastFragment() {
        // Required empty public constructor
    }

    public static ViewForecastFragment newInstance(int position) {
        ViewForecastFragment fragment = new ViewForecastFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_PARAM);
        }
        forecastManager = ForecastManager.getInstance(getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LineChart chart = getView().findViewById(R.id.resultChart);

        // Converts input series to dataset
        ArrayList<Entry> entriesInput = new ArrayList<>();
        double[] input = forecastManager.getCurrentForecast().getForecastInputs().get(mPosition).getSeries();
        for(int i = 0; i < input.length; i++){
            entriesInput.add(new Entry(i, (float) input[i]));
        }
        LineDataSet inputDataSet = new LineDataSet(entriesInput, forecastManager.getCurrentForecast().getForecastInputs().get(mPosition).getName());
        inputDataSet.setDrawCircles(false);
        inputDataSet.setLineWidth(LINE_WIDTH);
        inputDataSet.setColor(Color.rgb(0, 0, 0));
        inputDataSet.setDrawValues(false);

        // Converts result series to dataset
        ArrayList<Entry> entriesResults = new ArrayList<>();
        entriesResults.add(new Entry(input.length-1, (float) input[input.length-1]));
        double[] results = forecastManager.getCurrentForecast().getForecastResults().get(mPosition).getSeries();
        for(int i = input.length; i < results.length + input.length -1; i++){
            entriesResults.add(new Entry(i, (float) results[i-input.length]));
        }
        LineDataSet resultsDataSet = new LineDataSet(entriesResults, RESULTS_LABEL);
        resultsDataSet.setDrawCircles(false);
        resultsDataSet.setLineWidth(LINE_WIDTH);
        resultsDataSet.setColor(Color.rgb(220, 20, 60));
        resultsDataSet.setDrawValues(false);

        // Converts upper series to dataset
        ArrayList<Entry> entriesUpper = new ArrayList<>();
        entriesUpper.add(new Entry(input.length-1, (float) input[input.length-1]));
        double[] upper = forecastManager.getCurrentForecast().getForecastResults().get(mPosition).getUpper();
        for(int i = input.length; i < upper.length + input.length -1; i++){
            entriesUpper.add(new Entry(i, (float) upper[i-input.length]));
        }
        LineDataSet upperDataSet = new LineDataSet(entriesUpper, UPPER_LABEL);
        upperDataSet.setDrawCircles(false);
        upperDataSet.setLineWidth(LINE_WIDTH);
        upperDataSet.setColor(Color.rgb(105, 105, 105));
        upperDataSet.enableDashedLine(10f, 10f, 0f);
        upperDataSet.setDrawValues(false);

        // Converts lower series to dataset
        ArrayList<Entry> entriesLower = new ArrayList<>();
        entriesLower.add(new Entry(input.length-1, (float) input[input.length-1]));
        double[] lower = forecastManager.getCurrentForecast().getForecastResults().get(mPosition).getLower();
        for(int i = input.length; i < results.length + input.length -1; i++){
            entriesLower.add(new Entry(i, (float) lower[i-input.length]));
        }
        LineDataSet lowerDataSet = new LineDataSet(entriesLower, LOWER_LABEL);
        lowerDataSet.setDrawCircles(false);
        lowerDataSet.setLineWidth(LINE_WIDTH);
        lowerDataSet.setColor(Color.rgb(105, 105, 105));
        lowerDataSet.enableDashedLine(10f, 10f, 0f);
        lowerDataSet.setDrawValues(false);

        // Add datasets to dataset array

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet> ();
        dataSets.add(inputDataSet);
        dataSets.add(resultsDataSet);
        dataSets.add(upperDataSet);
        dataSets.add(lowerDataSet);

        LineData data = new LineData(dataSets);

        chart.setData(data);

        // Strings for x-axis labels
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getXAxisValues()));
        chart.setDrawGridBackground(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.getDescription().setEnabled(false);
    }

    private ArrayList<String> getXAxisValues()
    {
        ArrayList<String> labels = new ArrayList<String> ();

        String[] inputLabels = forecastManager.getCurrentForecast().getForecastInputs().get(mPosition).getTime();
        String[] resultsLabels = forecastManager.getCurrentForecast().getForecastResults().get(mPosition).getTime();
        for(int i = 0; i < inputLabels.length + resultsLabels.length - 1; i++){
            if(i < inputLabels.length){
                labels.add(inputLabels[i]);
            }else{
                labels.add(resultsLabels[i-inputLabels.length]);
            }
        }
        return labels;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_forecast, container, false);
    }
}