package com.example.foodplannerapp.view_model;

import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.models.AllCategoriesResponse;
import retrofit2.Call;

public class SearchViewModel {

    public Call<AllCategoriesResponse> getAllCategories(){


        Call<AllCategoriesResponse> mealModelCall= MealsRemoteDataSource.apiServices.getAllCategories("list");

        return mealModelCall;
    }
}
