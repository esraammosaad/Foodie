package com.example.foodplannerapp.data.network.database;



public interface FireStoreCallBack {

    void onFireStoreSuccess(String message);
    void onFireStoreFailure(String errorMessage);
}
