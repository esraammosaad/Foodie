package com.example.foodplannerapp.data.network.database;



public interface RemoteDatabaseCallBack {

    void onFireStoreSuccess(String message);
    void onFireStoreFailure(String errorMessage);
}
