package com.example.foodplannerapp.authentication.view;

public interface ViewInterface {
    void onGoogleLoginSuccess(String message);

    void onSuccess(String message);
    void onFailure(String message);

}
