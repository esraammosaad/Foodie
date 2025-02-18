package com.example.foodplannerapp.home.view;

import com.example.foodplannerapp.data.models.Meal;

import java.util.List;

public interface ViewInterface {

    void getRandomMeal(Meal meal);
    void getMealsByFirstLetter(List<Meal> mealList);
    void onFailure(String errorMessage);



}
