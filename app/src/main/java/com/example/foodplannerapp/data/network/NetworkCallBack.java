package com.example.foodplannerapp.data.network;

import com.example.foodplannerapp.data.models.Meal;

import java.util.List;

public interface NetworkCallBack {

    void onSuccess(Meal meal, List<Meal> mealList);
    void onFailure(String errorMessage);
}
