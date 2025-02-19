package com.example.foodplannerapp.authentication.data.network;

public interface AuthenticationCallBack {

    void onSuccess(String message);
    void onFailure(String errorMessage);
}
