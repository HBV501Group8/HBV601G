package com.example.hagspar.forecast.data;


import com.example.hagspar.usermanagement.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Forecast {
/*
    Forecast.java
    Object used to store forecasts.
    JPA:
        - Many-to-One relationship with User
        - One-to-Many relationship with ForecastInput
        - One-to-Many relationship with ForecastResult

    List of methods
        - drawForecast: Draws a chart of a specific series
        - Basic Getters and Setters
*/


    public String id;

    private String forecastName;

    private String generatedTime;

    private List<ForecastResult> forecastResults = new ArrayList<>();

    private List<ForecastInput> forecastInputs = new ArrayList<>();

    // Constructor is empty since construction is handled by the ForeastGeneratorService class
    public Forecast(String id, String forecastName, String generatedTime,
                    List<ForecastResult> forecastResults, List<ForecastInput> forecastInputs) {
        this.id = id;
        this.forecastName = forecastName;
        this.generatedTime = generatedTime;
        this.forecastResults = forecastResults;
        this.forecastInputs = forecastInputs;
    }

    /*
        drawForecast
            Use: Catches url query "./registeruser"
            Parameters: seriesName, name of series to be drawn
            Returns: JFreeChart with input values as an unbroken line and forecast
                     values as a dotted line with confidence intervals
    */

    // Getters and setters

    public String getForecastName() {
        return forecastName;
    }

    public void setForecastName(String forecastName) {
        this.forecastName = forecastName;
    }

    public String getGeneratedTime() { return generatedTime; }

    public void setGeneratedTime(String generatedTime) { this.generatedTime = generatedTime; }

    public List<ForecastResult> getForecastResults() {
        return forecastResults;
    }

    public void setForecastResults(List<ForecastResult> forecastResults) {
        this.forecastResults = forecastResults;
    }

    public List<ForecastInput> getForecastInputs() {
        return forecastInputs;
    }

    public void setForecastInputs(List<ForecastInput> forecastInputs) {
        this.forecastInputs = forecastInputs;
    }

}
