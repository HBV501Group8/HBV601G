package com.example.hagspar.forecast;

public interface ForcastManagerCallback <T> {
    void onSuccess(T result);
    void onFail(String error);
}
