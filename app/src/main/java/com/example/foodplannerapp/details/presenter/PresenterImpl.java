package com.example.foodplannerapp.details.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.data.local.model.MealLocalModel;
import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class PresenterImpl {

    MealsRepositoryImpl mealsRepository;


    public PresenterImpl(MealsRepositoryImpl mealsRepository) {

        this.mealsRepository = mealsRepository;
    }



   public ArrayList<Ingredient> getIngredients(Meal meal){
        return  mealsRepository.getIngredientsList(meal);

    }

    public void addMealToFav(MealLocalModel meal){

        mealsRepository.addMealToFavorite(meal);
    }
    public void deleteMealFromFav(MealLocalModel meal){

        mealsRepository.deleteMealFromFavorite(meal);
    }
    public LiveData<List<MealLocalModel>> getAllFavoriteMeals(){
        return mealsRepository.getAllFavoriteMeals();
    }
}
