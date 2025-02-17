package com.example.foodplannerapp.view_model;

import com.example.foodplannerapp.services.RetrofitFactory;
import com.example.foodplannerapp.models.MealModel;
import retrofit2.Call;

public class MealDetailsViewModel {


    public Call<MealModel> getMealDetails(Integer id){


        Call<MealModel> mealModelCall=RetrofitFactory.apiServices.getMealByID(id);

        return mealModelCall;
    }





}
