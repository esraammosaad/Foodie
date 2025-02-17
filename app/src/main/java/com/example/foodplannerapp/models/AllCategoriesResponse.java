

package com.example.foodplannerapp.models;
import java.util.List;

public class AllCategoriesResponse {
    private List<MealCategoriesResponse> meals;

    public List<MealCategoriesResponse> getMeals() { return meals; }
    public void setMeals(List<MealCategoriesResponse> value) { this.meals = value; }
}



