package com.example.foodplannerapp.view_model;

import com.example.foodplannerapp.data.models.MealModel;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;

import retrofit2.Call;

public class HomeViewModel {


    public Call<MealModel> getRandomMeal(){


        Call<MealModel> mealModelCall= MealsRemoteDataSource.apiServices.getRandomMeal();

        return mealModelCall;
    }

    public Call<MealModel> getMealsByFirstLetter(String firstLetter){


       Call<MealModel> mealModelCall= MealsRemoteDataSource.apiServices.getMealsByFirstLetter(firstLetter);

       return mealModelCall;

    }


}
