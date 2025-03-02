

package com.example.foodplannerapp.data.model;
import java.util.List;

public class MealModel {

    private List<Meal> meals;

    public MealModel(List<Meal> meals) {
        this.meals = meals;
    }

    public MealModel() {
    }


    public List<Meal> getMeals() { return meals; }
    public void setMeals(List<Meal> value) { this.meals = value; }
}



