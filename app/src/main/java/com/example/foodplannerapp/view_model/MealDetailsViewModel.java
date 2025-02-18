package com.example.foodplannerapp.view_model;

import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.models.MealModel;
import retrofit2.Call;

public class MealDetailsViewModel {


    public Call<MealModel> getMealDetails(Integer id){


        Call<MealModel> mealModelCall= MealsRemoteDataSource.apiServices.getMealByID(id);

        return mealModelCall;
    }





}
