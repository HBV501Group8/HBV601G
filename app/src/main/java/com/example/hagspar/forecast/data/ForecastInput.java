package com.example.hagspar.forecast.data;


import java.time.LocalDate;

public class ForecastInput {
/*
    ForecastInput.java
    Object used to store forecast Inputs.

    List of methods
        - Basic Getters and Setters
*/

    private long id;


    private String name;
    private String frequency; // m monthly, q quarterly, y yearly
    private String unit;

    private double[] series;

    private String[] time;

    // Constructor is empty since construction is handled by the ForeastGeneratorService class
    public ForecastInput() { }

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

    public void setSeries(double[] series) {
        this.series = series;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time.clone();
    }

}
