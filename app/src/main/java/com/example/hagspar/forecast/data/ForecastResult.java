package com.example.hagspar.forecast.data;

import java.time.LocalDate;


public class ForecastResult {
/*
    ForecastResult.java
    Object used to store forecast results

    List of methods
        - Basic Getters and Setters
*/
    private long id;

    private String name;
    private String frequency; // m monthly, q quarterly, y yearly
    private String unit;

    private double[] series;

    private double[] lower;

    private double[] upper;

    private String[] time;

    private String forecastModel;

    private String forecastDescription;

    // Constructor is empty since construction is handled by the ForeastGeneratorService class
    public ForecastResult() { }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUnit() { return unit; }

    public void setUnit(String unit) { this.unit = unit; }

    public double[] getSeries() {
        return series;
    }

    public void setSeries(double[] resultSeries) {
        this.series = resultSeries;
    }

    public double[] getLower() {
        return lower;
    }

    public void setLower(double[] lower) {
        this.lower = lower;
    }

    public double[] getUpper() {
        return upper;
    }

    public void setUpper(double[] upper) {
        this.upper = upper;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] resultTime) {
        this.time = resultTime.clone();
    }

    public String getForecastModel() {
        return forecastModel;
    }

    public void setForecastModel(String forecastModel) {
        this.forecastModel = forecastModel;
    }

    public String getForecastDescription() {
        return forecastDescription;
    }

    public void setForecastDescription(String forecastDescription) {
        this.forecastDescription = forecastDescription;
    }
}
