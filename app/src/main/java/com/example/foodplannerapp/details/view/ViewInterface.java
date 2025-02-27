package com.example.foodplannerapp.details.view;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.models.Meal;

import java.util.List;

public interface ViewInterface {
    void onFavoriteDatabaseSuccess(List<FavoriteMealModel> favoriteMealModel);
    void onCalendarDatabaseSuccess(List<CalenderMealModel> calenderMealModel);
    void onFavoriteMealSuccessfullyAddedToFireStore(String message);
    void onFavoriteMealFailedToAddedToFireStore(String errorMessage);
    void onSuccess(Meal meal);
    void onFailure(String errorMessage);
}
