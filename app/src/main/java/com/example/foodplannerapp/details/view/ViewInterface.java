package com.example.foodplannerapp.details.view;

import com.example.foodplannerapp.data.models.Meal;

public interface ViewInterface {
    void onSuccess(Meal meal);
    void onFavoriteMealAddedToFireStore(String message);
    void onFavoriteMealFailedToAddedToFireStore(String errorMessage);
    void onFailure(String errorMessage);
}
