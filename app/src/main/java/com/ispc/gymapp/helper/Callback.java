package com.ispc.gymapp.helper;

public interface Callback<T>{
    void onSuccess(T result);
    void onFailure();
}
