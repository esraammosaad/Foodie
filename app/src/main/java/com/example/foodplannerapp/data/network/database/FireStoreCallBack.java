package com.example.foodplannerapp.data.network.database;

import com.example.foodplannerapp.data.local.model.FavoriteMealModel;

import java.util.List;

public interface FireStoreCallBack {

    void onFireStoreSuccess(String message);
    void onFireStoreFailure(String errorMessage);
}
