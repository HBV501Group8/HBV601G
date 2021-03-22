package com.example.hagspar.networking;

public interface NetworkCallback<T> {

    void onSuccess(T result);

    void onFail(String error);

}
