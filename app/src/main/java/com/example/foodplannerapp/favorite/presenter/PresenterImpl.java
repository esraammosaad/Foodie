package com.example.foodplannerapp.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.data.local.model.MealLocalModel;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;

import java.util.List;

public class PresenterImpl {
    MealsRepositoryImpl mealsRepository;


    public PresenterImpl(MealsRepositoryImpl mealsRepository) {

        this.mealsRepository = mealsRepository;
    }



    public LiveData<List<MealLocalModel>> getAllFavoriteMeals(String userUID){
        return mealsRepository.getAllFavoriteMeals(userUID);
    }

    public void deleteMealFromFavorite(MealLocalModel meal){

        mealsRepository.deleteMealFromFavorite(meal);

    }
    public void addMealToFavorite(MealLocalModel meal){

        mealsRepository.addMealToFavorite(meal);

    }













}
