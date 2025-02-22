package com.example.foodplannerapp.details.view;

import com.example.foodplannerapp.data.models.Meal;

public interface ViewInterface {
    void onSuccess(Meal meal);

    void onFailure(String errorMessage);
}
