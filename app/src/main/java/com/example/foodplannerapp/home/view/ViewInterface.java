package com.example.foodplannerapp.home.view;

import com.example.foodplannerapp.data.model.Meal;

import java.util.List;

public interface ViewInterface {
    void getRandomMeal(Meal meal);

    void getMealsByFirstLetter(List<Meal> mealList);

    void onFailure(String errorMessage);

}
