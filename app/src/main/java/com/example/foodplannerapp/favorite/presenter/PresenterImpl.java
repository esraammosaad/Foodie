package com.example.foodplannerapp.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.data.local.model.MealLocalModel;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;

import java.util.List;

public class PresenterImpl {
    MealsRepositoryImpl mealsRepository;

    private static PresenterImpl instance = null;

    public PresenterImpl(MealsRepositoryImpl mealsRepository) {

        this.mealsRepository = mealsRepository;
    }



    public LiveData<List<MealLocalModel>> getAllFavoriteMeals(){
        return mealsRepository.getAllFavoriteMeals();
    }









}
