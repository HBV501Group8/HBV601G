package com.example.hagspar.forecast;

public interface ForecastCallback<T> {
    void whenReady(T ready);
}
