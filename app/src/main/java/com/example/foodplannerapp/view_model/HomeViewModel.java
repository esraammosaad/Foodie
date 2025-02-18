package com.example.foodplannerapp.view_model;

import com.example.foodplannerapp.data.models.MealModel;
import com.example.foodplannerapp.data.network.RetrofitFactory;

import retrofit2.Call;

public class HomeViewModel {


    public Call<MealModel> getRandomMeal(){


        Call<MealModel> mealModelCall=RetrofitFactory.apiServices.getRandomMeal();

        return mealModelCall;
    }

    public Call<MealModel> getMealsByFirstLetter(String firstLetter){


       Call<MealModel> mealModelCall=RetrofitFactory.apiServices.getMealsByFirstLetter(firstLetter);

       return mealModelCall;

    }


}
